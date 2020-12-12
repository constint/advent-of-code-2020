(ns aoc2020.day8
  (:require [clojure.spec.alpha :as s]))

(defn acc [arg]
  {:acc-offset        (Integer/parseInt arg)
   :next-instr-offset 1})

(defn jmp [arg]
  {:acc-offset        0
   :next-instr-offset (Integer/parseInt arg)})

(defn nop [arg]
  {:acc-offset        0
   :next-instr-offset 1})

(defn exec-instr [[code arg]]
  ;  (prn "exec" code "arg" arg)
  (condp = code
    "acc" (acc arg)
    "jmp" (jmp arg)
    "nop" (nop arg)))

(defn last-accumulator-value [instructions]
  (loop [instr-idx         0
         acc               0
         visited-instr-idx #{}]
    (let [{acc-ofsset :acc-offset next-instr-offset :next-instr-offset} (exec-instr (nth instructions instr-idx))
          next-instr-idx                                                (+ instr-idx next-instr-offset)
          next-acc                                                      (+ acc acc-ofsset)]
      (if (visited-instr-idx next-instr-idx) ;; We're heading into a loop
        next-acc
        (recur next-instr-idx next-acc (conj visited-instr-idx instr-idx))))))

(defn input->instructions [input]
  (->> input
       (.trim)
       (clojure.string/split-lines)
       (mapv (fn [instr-string] (rest (re-matches #"([a-z]+) (.+)" instr-string))))))

;;; PART 2
*
(defn acc-if-terminates-correctly [instructions]
  (loop [instr-idx         0
         acc               0
         visited-instr-idx #{}]
    (let [{acc-ofsset :acc-offset next-instr-offset :next-instr-offset} (exec-instr (nth instructions instr-idx))
          next-instr-idx                                                (+ instr-idx next-instr-offset)
          next-acc                                                      (+ acc acc-ofsset)]
      (if-not (visited-instr-idx next-instr-idx) ;; We're heading into a loop
        (if (= next-instr-idx (count instructions))
          next-acc
          (recur next-instr-idx next-acc (conj visited-instr-idx instr-idx)))))))

(defn try-replace [instructions idx]
  (let [[code arg] (nth instructions idx)]
    (condp = code
      "jmp" (acc-if-terminates-correctly (assoc instructions idx ["nop" arg]))
      "nop" (acc-if-terminates-correctly (assoc instructions idx ["jmp" arg]))
      nil)))

(defn last-acc-for-fixed-program [input]
  (let [instructions (input->instructions input)]
    (first
     (filter some?
             (for [i (range (count instructions))]
               (try-replace instructions i))))))
