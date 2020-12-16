(ns aoc2020.day12-test
  (:require [clojure.test :refer :all]
            [aoc2020.day12 :refer :all]
            [clojure.spec.alpha :as s]))

(def input (slurp "resources/day12.txt"))

(def testinput
  "F10
N3
F7
R90
F11")

(deftest simple-test
  (testing "Check with simple test"
           (is
            (= (input->answer1 testinput)
               25))))

(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is
            (= (input->answer1 input)
               2458))))

(deftest simple-test-2
  (testing
   "Check with simple test part2"
   (is
    (= (input->answer2 testinput)
       286))))

(deftest check-answer1
  (testing "Answer part 2 is correct"
           (is
            (= (input->answer2 input)
               145117))))