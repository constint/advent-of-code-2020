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