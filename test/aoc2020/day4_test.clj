(ns aoc2020.day4-test
  (:require [clojure.test :refer :all]
            [aoc2020.day4 :refer :all]
            [clojure.spec.alpha :as s]))

(def input (slurp "resources/day4.txt"))


(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is (= (valid-password-count  input) 250))))
;
;
;(deftest check-answer2
;  (testing "Answer part 2 is correct"
;           (is
;            (=
;             (slopes-tree-multiple input
;                                   [[1 1] [3 1] [5 1] [7 1] [1 2]])
;             3584591857))))

(run-all-tests)