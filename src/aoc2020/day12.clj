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

(def starting-position {:east-pos 0 :north-pos 0 :direction "E"})

(defn perform-move [{:keys [direction] :as current} {:keys [move-direction move-value]}]
  (condp = move-direction
    "N" (update-in current [:north-pos] + move-value)
    "S" (update-in current [:north-pos] - move-value)
    "E" (update-in current [:east-pos] + move-value)
    "W" (update-in current [:east-pos] - move-value)
    "L" (update-in current [:direction] (partial rotate left-rotations) move-value)
    "R" (update-in current [:direction] (partial rotate right-rotations) move-value)
    "F" (perform-move current {:move-direction direction :move-value move-value})))

(defn manhatan [{:keys [east-pos north-pos]}]
  (+ (Math/abs east-pos) (Math/abs north-pos)))

(defn apply-moves [moves]
  (reduce perform-move starting-position moves))

(defn input->answer1 [input]
  (manhatan (apply-moves (input->moves input))))