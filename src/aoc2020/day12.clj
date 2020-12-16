(ns aoc2020.day12
  (:require [clojure.spec.alpha :as s]))

(defn parse-move [move-str]
  (let [[_ direction val] (re-matches #"([A-Z])(\d+)" move-str)]
    {:move-direction direction
     :move-value     (Integer/parseInt val)}))

(defn input->moves [input]
  (->> input
       (.trim)
       clojure.string/split-lines
       (map parse-move)))

(def left-rotations (cycle ["E" "N" "W" "S"]))
(def right-rotations (cycle ["E" "S" "W" "N"]))

(defn rotate [rotations-cycle current-dir angle]
  (let [rotations-count (/ angle 90)
        rotations       (drop-while #(not= current-dir %) rotations-cycle)]
    (nth rotations rotations-count)))

(def starting-configuration {:position {:east 0 :north 0} :direction "E" :waypoint {:east 10 :north 1}})

(defn perform-move [{:keys [direction] :as current} {:keys [move-direction move-value]}]
  (condp = move-direction
    "N" (update-in current [:position :north] + move-value)
    "S" (update-in current [:position :north] - move-value)
    "E" (update-in current [:position :east] + move-value)
    "W" (update-in current [:position :east] - move-value)
    "L" (update-in current [:direction] (partial rotate left-rotations) move-value)
    "R" (update-in current [:direction] (partial rotate right-rotations) move-value)
    "F" (perform-move current {:move-direction direction :move-value move-value})))

(defn manhatan [{{:keys [east north]} :position}]
  (+ (Math/abs east) (Math/abs north)))

(defn apply-moves [moves]
  (reduce perform-move starting-configuration moves))

(defn input->answer1 [input]
  (manhatan (apply-moves (input->moves input))))

;; PART 2

(defn move-waypoint [waypoint direction value]
  (update-in waypoint [direction] + value))

(defn move-position [{waypt-east :east waypt-north :north} {pos-east :east pos-norht :north} value]
  {:east  (+ pos-east (* value waypt-east))
   :north (+ pos-norht (* value waypt-north))})

;; Quick and dirty let's not bother with propre matrix multiplication
;; NB x = east, y = west https://en.wikipedia.org/wiki/Rotation_matrix
(defn rotate-left [{x :east y :north} angle]
  (condp = angle
    90  {:east (- y) :north x}
    180 {:east (- x) :north (- y)}
    270 {:east y :north (- x)}))

(defn rotate-right [{x :east y :north} angle]
  (condp = angle
    90  {:east y :north (- x)}
    180 {:east (- x) :north (- y)}
    270 {:east (- y) :north x}))

(defn perform-move-v2 [{:keys [position waypoint] :as current} {:keys [move-direction move-value]}]
;  (prn position waypoint)
  (condp = move-direction
    "N" (update-in current [:waypoint] #(move-waypoint % :north move-value))
    "S" (update-in current [:waypoint] #(move-waypoint % :north (- move-value)))
    "E" (update-in current [:waypoint] #(move-waypoint % :east move-value))
    "W" (update-in current [:waypoint] #(move-waypoint % :east (- move-value)))
    "L" (update-in current [:waypoint] rotate-left move-value)
    "R" (update-in current [:waypoint] rotate-right move-value)
    "F" (update-in current [:position] (partial move-position waypoint) move-value)))

(defn apply-moves-v2 [moves]
  (reduce perform-move-v2 starting-configuration moves))

(defn input->answer2 [input]
  (manhatan (apply-moves-v2 (input->moves input))))