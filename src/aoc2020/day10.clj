(ns aoc2020.day10
  (:require [clojure.spec.alpha :as s]))

(defn input->numbers [input]
  (->> input
       .trim
       clojure.string/split-lines
       (map #(Long/parseLong %))))

(defn get-diffs-all-adapters [nums]
  (loop [diffs    {}
         last     0
         [x & xs] (sort nums)]
    (if (nil? x)
      ;; Last diff always 3
      (update-in diffs [3] (fnil inc 0))
      (let [diff      (- x last)
            new-diffs (update-in diffs [diff] (fnil inc 0))]
        (recur new-diffs x xs)))))

;; PART 2
