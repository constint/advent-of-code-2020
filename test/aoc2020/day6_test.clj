(ns aoc2020.day6-test
  (:require [clojure.test :refer :all]
            [aoc2020.day6 :refer :all]
            [clojure.spec.alpha :as s]))

(def input (slurp "resources/day6.txt"))

(def testinput "abc

a
b
c

ab
ac

a
a
a
a

b")

(deftest simple-test
  (testing "Check with simple test"
           (is (= (answer1 testinput) 11))
           (is (= (answer2 testinput) 6))))

(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is (= (answer1 input) 6633))))

(deftest check-answer2
  (testing "Answer part 2 is correct"
           (is (= (answer2 input) 3202))))
