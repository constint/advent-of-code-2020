(ns aoc2020.day13
  (:require [clojure.spec.alpha :as s]))

(defn earliest-pass-after [timestamp id]
  (if (integer? (/ timestamp id))
    timestamp
    (let [elapsed-rotations (Math/floorDiv timestamp id)]
      (* id (+ elapsed-rotations 1)))))

(defn earliest-bus [timestamp bus-ids]
  (first (sort-by (partial earliest-pass-after timestamp) bus-ids)))

(defn answer1 [timestamp bus-ids]
  (let [bus       (earliest-bus timestamp bus-ids)
        departure (earliest-pass-after timestamp bus)]
    (* bus (- departure timestamp))))

;; PART 2
(defn input->bus-offset-pairs [input]
  (->>
   (clojure.string/replace input
                           "x" "\"x\"")
   read-string
   (map-indexed (fn [index bus] [bus index]))
   (filter #(not= "x" (first %)))))

;; To make the search easier, we will normalize bus timestamps by substracting their respective offset
;; This way we need to find the first "timestamp" when all the buses arrive together

(defn normalized-first-pass-after
  [timestamp [bus offset]]
  (- (earliest-pass-after timestamp bus) offset))

(defn has-bus? [timestamp bus]
  (zero? (mod timestamp bus)))

(defn timestamp-correct? [bus-pairs timestamp]
  (loop [bus-pairs bus-pairs]
    (if-not (seq bus-pairs)
      timestamp
      (let [[bus offset] (first bus-pairs)]
        (if-not (has-bus? (+ timestamp offset) bus)
          false
          (recur (rest bus-pairs)))))))

(defn answer2
  [input]
  (let [bus-offset-pairs (input->bus-offset-pairs input)
        first-bus        (ffirst bus-offset-pairs)]
    (loop [timestamp first-bus]
      ;     (if (mod timestamp 100000)
      ;      (prn "timestamp" timestamp))
      (if (timestamp-correct? bus-offset-pairs timestamp)
        timestamp
        (recur (+ timestamp first-bus))))))

;; A bit slower than the recur approach
(defn answer2bis
  [input]
  (let [bus-offset-pairs (input->bus-offset-pairs input)
        first-bus        (ffirst bus-offset-pairs)]
    (some (partial timestamp-correct? bus-offset-pairs)
          (iterate (partial + first-bus) first-bus))))


(defn answer2ter
  "Optimize by using largest bus id as basis for search (we increase the timestamp by the largest amount each time"
  ([input]
   (answer2ter input 1))
  ([input start-at]
   (let [bus-offset-pairs                           (input->bus-offset-pairs input)
         [largest-bus-id largest-bus-offset]        (last (sort-by first bus-offset-pairs))]
     (loop [timestamp (earliest-pass-after start-at largest-bus-id)]
       ;     (if (mod timestamp 100000)
       ;      (prn "timestamp" timestamp))
       (if (timestamp-correct? bus-offset-pairs (- timestamp largest-bus-offset))
         timestamp
         (recur (+ timestamp largest-bus-id)))))))

(defn answser2-crt [input]
  "  This problem can be posed as: find timestamp t where
  t --- offset1 (mod bus1)
  t --- offset2 (mod bus2)
  ....
  Try using the chinese remainder theorem to find the answer in a sane amount of time")