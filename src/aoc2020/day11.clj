(ns aoc2020.day11
  (:require [clojure.spec.alpha :as s]))

(defn input->matrix [input]
  (->> input
       (.trim)
       clojure.string/split-lines
       (mapv (comp vec seq))))

(defn matrix->points-coords [matrix]
  (let [xmax (count matrix)
        ymax (count (first matrix))]
    (for [x (range 0 xmax)
          y (range 0 ymax)]
      [x y])))


(defn point->neighbours [xmax ymax [x0 y0]]
  (for [x     (range (- x0 1) (+ x0 2))
        y     (range (- y0 1) (+ y0 2))
        :when (and (<= 0 x xmax)
                   (<= 0 y ymax)
                   (not= [x y] [x0 y0]))]
    [x y]))

(defn get-point [matrix [x y]]
  (nth (nth matrix x) y))

(defn next-value [current-val occupied-count]
  (condp = current-val
    \L (if (zero? occupied-count) \# \L)
    \# (if (< occupied-count 4) \# \L)))

(defn point-coord->next-value-v1
  "Returns a pair containing next point value and modified indicating if it was modified"
  [matrix xmax ymax point]
  ;  (prn "process point" point)
  (if (= \. (get-point matrix point))
    ;; Ground, not modified
    [\. false]
    ;; Seat
    (let [seat-val           (get-point matrix point)
          neighbours         (point->neighbours xmax ymax point)
          occupied-count     (->> neighbours
                                  (map (partial get-point matrix))
                                  (filter #{\#})
                                  (count))
          next-val           (next-value seat-val occupied-count)]
      [next-val (not= seat-val next-val)])))

(defn print-matrix [matrix]
  (run! #(println (apply str %)) matrix))

(defn count-occupied [matrix]
  (->> matrix
       flatten
       (filter #{\#})
       count))

(defn stabilize-matrix [matrix point-coord->next-value]
  (let [xmax          (- (count matrix) 1)
        ymax          (- (count (first matrix)) 1)
        points        (matrix->points-coords matrix)]
    (loop [m     matrix
           step  0]
      (let [point->next    (partial point-coord->next-value m xmax ymax)
            new-points     (map point->next points)
            updated-count  (->> new-points
                                (map second)
                                (filter true?)
                                count)
            new-matrix     (->> new-points
                                (map first)
                                (partition (+ ymax 1))
                                (map vec)
                                vec)]
;        (prn "step" step)
;        (print-matrix new-matrix)
;        (prn)
        (if (zero? updated-count)
          new-matrix
          (recur new-matrix (inc step)))))))

(defn stabilize-matrix-v1 [matrix]
  (stabilize-matrix matrix point-coord->next-value-v1))

;; Day2

(def directions [[1 0] [1 -1] [0 -1] [-1 -1] [-1 0] [-1 1] [0 1] [1 1]])

(defn occupied-direction? [matrix xmax ymax [x0 y0] [dx dy]]
  (loop [x (+ x0 dx)
         y (+ y0 dy)]
    (if-not
      (and (<= 0 x xmax)
           (<= 0 y ymax))
      false
      (condp =  (get-point matrix [x y])
        \# true
        \L false
        (recur (+ x dx) (+ y dy))))))

(defn next-value-2 [current-val occupied-count]
  (condp = current-val
    \L (if (zero? occupied-count) \# \L)
    \# (if (< occupied-count 5) \# \L)))

(defn point-coord->next-value-v2
  "Returns a pair containing next point value and modified indicating if it was modified"
  [matrix xmax ymax point]
  ;  (prn "process point" point)
  (if (= \. (get-point matrix point))
    ;; Ground, not modified
    [\. false]
    ;; Seat
    (let [seat-val           (get-point matrix point)
          occupied-count     (->> directions
                                  (map (partial occupied-direction? matrix xmax ymax point))
                                  (filter true?)
                                  (count))
          next-val           (next-value-2 seat-val occupied-count)]
      [next-val (not= seat-val next-val)])))

(defn stabilize-matrix-v2 [matrix]
  (stabilize-matrix matrix point-coord->next-value-v2))
