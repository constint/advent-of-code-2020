(ns aoc2020.day6
  (:require [clojure.spec.alpha :as s]))

(defn count-unique-answers [answer-string]
  (let [answers (filter #(not= \newline %) answer-string)]
    (count (set answers))))

(defn answer1
  "For each group, count the number of questions to which anyone answered yes. What is the sum of those counts?"
  [input]
  (->> (clojure.string/split input #"\n\n")
       (map count-unique-answers)
       (apply +)))

(defn count-unanimous-answers [answer-string]
  (let [answer-sets           (map set (clojure.string/split answer-string #"\n"))
        unanimous-answer-sets (apply clojure.set/intersection answer-sets)]
    (count unanimous-answer-sets)))

(defn answer2
  "For each group, count the number of questions to which everyone answered yes. What is the sum of those counts?"
  [input]
  (->> (clojure.string/split input #"\n\n")
       (map count-unanimous-answers)
       (apply +)))