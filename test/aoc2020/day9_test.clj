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
           (is (= (find-invalid-value (input->numbers input) 25) 1384))))

;
;(deftest simple-test-2
;  (testing "Check with simple test for answer2"
;           (is (= "" 8))))
;;
;(deftest check-answer2
;  (testing "Answer part 2 is correct"
;           (is (= "" 761))))
;(run-all-tests)
