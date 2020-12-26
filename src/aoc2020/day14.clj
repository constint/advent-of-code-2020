(ns aoc2020.day14
  (:require [clojure.spec.alpha :as s]))

(defn bit->int [idx bit-char]
  (if (= bit-char \1)
    (long (Math/pow 2 idx))
    0))

(defn bitstring->int [str]
  (reduce + (map-indexed bit->int (reverse str))))

(defn or-mask
  "Or mask to turn bits to 1"
  [string]
  (->
   (clojure.string/replace string #"X" "0")
   bitstring->int))

(defn and-mask
  "And mask to turn bits to 0"
  [string]
  (->
   (clojure.string/replace string #"X" "1")
   bitstring->int))

(defn apply-mask [mask value]
  (-> value
      (bit-or (or-mask mask))
      (bit-and (and-mask mask))))

;; Parsing

(defn- parse-mem-assign [mem-str]
  (let [[_ target value] (re-matches #"mem\[(\d+)\] = (\d+)" mem-str)]
    {:target (Integer/parseInt target)
     :value  (Integer/parseInt value)}))

(defn- parse-group [[mask-str & mem-strs]]
  {:mask            (second (re-matches #"mask = (.+)" mask-str))
   :mem-assignments (map parse-mem-assign mem-strs)})

(defn parse-input
  "Parse input to format:
  ({:mask \"111101X010011110100100110100101X0X0X\", :mem-assignments ({:target 37049, :value 1010356} {:target 5632, :value 28913} {:target 9384, :value 7522})} "
  [input]
  (let [mask-and-assignment-groups (->> input
                                        .trim
                                        (clojure.string/split-lines)
                                        (partition-by #(.startsWith % "mask"))
                                        (partition 2)
                                        (map flatten))]
    (map parse-group mask-and-assignment-groups)))

;; Memory operations
(defn apply-with-mask [mask memory {:keys [target value]}]
  (assoc memory target (apply-mask mask value)))

(defn apply-changes [memory {:keys [mask mem-assignments]}]
  (reduce (partial apply-with-mask mask) memory mem-assignments))

(defn input->answer1 [input]
  (->> input
       parse-input
       (reduce apply-changes
               {} ; Initial memory state (nothing is zero)
               )
       (vals) ;; Keep only memory values
       (reduce +) ;; Sum all values
       ))