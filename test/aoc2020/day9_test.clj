(ns aoc2020.day9-test
  (:require [clojure.test :refer :all]
            [aoc2020.day9 :refer :all]
            [clojure.spec.alpha :as s]))

(def input (slurp "resources/day9.txt"))

(def testinput
  [35
   20
   15
   25
   47
   40
   62
   55
   65
   95
   102
   117
   150
   182
   127
   219
   299
   277
   309
   576])

(deftest simple-test
  (testing
   "Check with simple test"
   (is
    (= (find-invalid-value testinput 5) 127))))

(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is (= (find-invalid-value (input->numbers input) 25) 552655238))))

(deftest simple-test-2
  (testing "Check with simple test for answer2"
           (is (= (answser2 testinput 127) 62))))

(deftest check-answer2
         (testing "Answer part 2 is correct"
                  (is (= (answser2 (input->numbers input) 552655238) 70672245))))

