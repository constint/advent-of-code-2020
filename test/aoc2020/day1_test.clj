(ns aoc2020.day1-test
  (:require [clojure.test :refer :all]
            [aoc2020.day1 :refer :all]))

(def input (read-string (slurp "resources/day1.edn")))

(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is (= (answer1 input) 1019571))))


(deftest check-answer2
  (testing "Answer part 2 is correct"
           (is (= (answer2 input) 100655544))))

(answer1 input)