(ns aoc2020.day10-test
  (:require [clojure.test :refer :all]
            [aoc2020.day10 :refer :all]
            [clojure.spec.alpha :as s]))

(def input (slurp "resources/day10.txt"))

(def testinput
  [16
   10
   15
   5
   1
   11
   7
   19
   6
   12
   4])

(deftest simple-test
  (testing
   "Check with simple test"
   (is
    (= (get-diffs-all-adapters testinput) {1 7, 3 5}))))

(def testinput2 [28
                 33
                 18
                 42
                 31
                 14
                 46
                 20
                 48
                 47
                 24
                 23
                 49
                 45
                 19
                 38
                 39
                 11
                 1
                 32
                 25
                 35
                 8
                 17
                 7
                 9
                 4
                 2
                 34
                 10
                 3])

(deftest simple-test-bis
  (testing
   "Check with another simple test"
   (is
    (= (get-diffs-all-adapters testinput2) {1 22, 3 10}))))

(deftest check-answer1
  (testing "Answer part 1 is correct"
           (let [{diff1 1 diff3 3} (get-diffs-all-adapters (input->numbers input))]
             (is (= (* diff1 diff3) 3034)))))
