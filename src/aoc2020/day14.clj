(ns aoc2020.day14
  (:require [clojure.spec.alpha :as s]))

(defn bit->int [idx bit-char]
  (if (= bit-char \1)
    (long (Math/pow 2 idx))
    0))

(defn bitseq->int [str]
  (reduce + (map-indexed bit->int (reverse str))))

(defn or-mask
  "Or mask to turn bits to 1"
  [string]
  (->
   (clojure.string/replace string #"X" "0")
   bitseq->int))

(defn and-mask
  "And mask to turn bits to 0"
  [string]
  (->
   (clojure.string/replace string #"X" "1")
   bitseq->int))

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

;; PART 2
(defn pad-to-36 [coll]
  (take 36 (concat coll (repeat 0))))

(defn x-mask [value mask]
  (let [bit-chars-r   (reverse (Long/toBinaryString value))
        mask-chars-r  (reverse mask)
        new-val-r     (map (fn [mask-char bit-char] (if (= \X mask-char) \X bit-char))
                           (pad-to-36 mask-chars-r) (pad-to-36 bit-chars-r))]
    (apply str (reverse new-val-r))))

(defn apply-mask-v2 [mask value]
  (-> value
      (bit-or (or-mask mask))
      (x-mask mask)))

(defn expand-address [[x & addr-rest]]
  (if (empty? addr-rest)
    (if (= \X x)
      [[\0] [\1]]
      [[x]])
    (let [exansion-rest (expand-address addr-rest)]
      (if (= \X x)
        (concat (map #(cons \0 %) exansion-rest)
                (map #(cons \1 %) exansion-rest))
        (map #(cons x %) exansion-rest)))))

;; Memory operations v2

(defn apply-with-mask-v2 [mask memory {:keys [target value]}]
  (let [mem-addresses  (map bitseq->int (expand-address (apply-mask-v2 mask target)))]
    ;(prn "writing val" value "to addresses" mem-addresses)
    (reduce (fn [mem addr] (assoc mem addr value))
            memory
            mem-addresses)))

(defn apply-changes-v2 [memory {:keys [mask mem-assignments]}]
  (reduce (partial apply-with-mask-v2 mask) memory mem-assignments))

(defn input->answer2 [input]
  (->> input
       parse-input
       (reduce apply-changes-v2
               {} ; Initial memory state (nothing is zero)
               )
       (vals) ;; Keep only memory values
       (reduce +) ;; Sum all values
       ))