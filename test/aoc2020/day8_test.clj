(ns aoc2020.day8-test
  (:require [clojure.test :refer :all]
            [aoc2020.day8 :refer :all]
            [clojure.spec.alpha :as s]))

(def input (slurp "resources/day8.txt"))

(def testinput
  "nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6")

(deftest simple-test
  (testing
   "Check with simple test"
   (is
    (= (last-accumulator-value (input->instructions testinput)) 5))))

(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is (= (last-accumulator-value (input->instructions input)) 1384))))

(deftest simple-test-2
  (testing "Check with simple test for answer2"
           (is (= (last-acc-for-fixed-program testinput) 8))))
;
(deftest check-answer2
  (testing "Answer part 2 is correct"
           (is (= (last-acc-for-fixed-program input) 761))))
(run-all-tests)
