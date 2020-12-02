(ns aoc2020.day2-test
  (:require [clojure.test :refer :all]
            [aoc2020.day2 :refer :all]))

(def input (slurp "resources/day2.txt"))

(deftest parsing
  (testing "Parse individual row"
           (is (= (parse-row "2-15 h: hhkhhhhhhhhhhhth") [2 15 "h" "hhkhhhhhhhhhhhth"]))))

(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is (= (answer1 input) 393))))

(deftest check-answer2
  (testing "Answer part 2 is correct"
           (is (= (answer2 input) 690))))
