(ns aoc2020.day9
  (:require [clojure.spec.alpha :as s]))

(defn input->numbers [input]
  (->> input
       .trim
       clojure.string/split-lines
       (map #(Long/parseLong %))))

(defn naive-valid? [preceding-window number]
  (let [sorted-window      (sort preceding-window)
        possible-sums      (for [x     sorted-window
                                 y     sorted-window
                                 :when (< x y)]
                             (+ x y))]
    ;(prn "window" preceding-window "num" number)
    ;(prn  "sums"(vec possible-sums))
    (some #{number} possible-sums)))

(defn find-invalid-value [numbers preamble-size]
  (loop [numbers numbers]
    (if-not (empty? numbers)
      (if-not (naive-valid? (take preamble-size numbers) (nth numbers preamble-size)) ;; Check number just after the preamble
        (nth numbers preamble-size)
        (recur (drop 1 numbers))))))

;; PART 2

(defn find-matching-sumlength [numbers value]
  (loop [numbers numbers
         sum     0
         index   0]
    (if (= sum value)
      index
      (if-not (> sum value)
        (if-some [x (first numbers)]
          (recur (rest numbers) (+ sum x) (+ index 1)))))))

(defn find-answer2-range [numbers sum]
  (loop [numbers numbers]
    (if-not (empty? numbers)
      (if-let [sum-length (find-matching-sumlength numbers sum)]
        (take sum-length numbers)
        (recur (drop 1 numbers))))))

(defn answser2 [numbers sum]
  (let [sorted-answer-range (sort  (find-answer2-range numbers sum))]
    (+ (first sorted-answer-range) (last sorted-answer-range))))
