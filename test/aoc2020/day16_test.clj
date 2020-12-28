(ns aoc2020.day16-test
  (:require [clojure.test :refer :all]
            [aoc2020.day16 :refer :all]
            [clojure.spec.alpha :as s]))

(def input (slurp "resources/day16.txt"))

(def testinput
  "class: 1-3 or 5-7
row: 6-11 or 33-44
seat: 13-40 or 45-50

your ticket:
7,1,14

nearby tickets:
7,3,47
40,4,50
55,2,20
38,6,12")

(deftest simple-test
  (testing "Check with simple test"
           (is
            (= (answer1 testinput) 71))))

(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is
            (= (answer1 input) 26988))))

(deftest check-answer2
  (testing "Answer part 2 is correct"
           (is
            (= (answer2 input) 426362917709))))