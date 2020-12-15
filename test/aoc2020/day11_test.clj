(ns aoc2020.day11-test
  (:require [clojure.test :refer :all]
            [aoc2020.day11 :refer :all]
            [clojure.spec.alpha :as s]))

(def input (slurp "resources/day11.txt"))

(def testinput
  "L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL
")

(deftest simple-test
  (testing
   "Check with simple test"
   (is
    (= (count-occupied (stabilize-matrix-v1 (input->matrix testinput)))
       37))))

(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is
             (= (count-occupied (stabilize-matrix-v1 (input->matrix input)))
                2093))))

(deftest simple-test-2
  (testing
   "Check with simple test part2"
   (is
    (= (count-occupied (stabilize-matrix-v2 (input->matrix testinput)))
       26))))

(deftest check-answer1
  (testing "Answer part 2 is correct"
           (is
             (= (count-occupied (stabilize-matrix-v2 (input->matrix input)))
                1862))))