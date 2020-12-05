(ns aoc2020.day5
  (:require [clojure.spec.alpha :as s]))

(defn input->seats
  "Takes the input, and returns a collection of seats, represented by a sequence of letter 'moves'"
  [input]
  (->> input
       (.trim)
       (clojure.string/split-lines)
       (map seq)))

(defn reduce-range-fn
  "Given a starting range and a letter, returns a new range"
  [[low high] letter]
  (condp = letter
    \B    [(int (Math/ceil (/ (+ high low) 2))) high]
    \R    [(int (Math/ceil (/ (+ low high) 2))) high]
    \F    [low (int (Math/floor (/ (+ high low) 2)))]
    \L    [low (int (Math/floor (/ (+ low high) 2)))]))

(defn seat->position [seat]
  (let [[row-moves col-moves] (split-at 7 seat)]
    [(first (reduce reduce-range-fn [0 127] row-moves))
     (first (reduce reduce-range-fn [0 7] col-moves))]))

(defn position->seat-id [[row col]]
  (+ (* row 8) col))

(defn max-seat-id [input]
  (->> (input->seats input)
       (map seat->position)
       (map position->seat-id)
       (apply max)))

(defn non-adjacent-seat-ids? [[id1 id2]]
  (not= 1 (Math/abs (- id2 id1))))

(defn input->free-id [input]
  (->> (input->seats input)
       (map seat->position)
       (map position->seat-id)
       (sort)
       (partition 2 1) ;; get seat id pairs, ie (1 2) (2 3) (3 4)
       (filter non-adjacent-seat-ids?) ;; get pair with gap ie (3 5)
       ((comp (partial + 1) ffirst)))) ;; get free id assumed at +1 from the lower id