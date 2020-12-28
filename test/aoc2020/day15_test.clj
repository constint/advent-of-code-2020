(ns aoc2020.day15-test
  (:require [clojure.test :refer :all]
            [aoc2020.day15 :refer :all]
            [clojure.spec.alpha :as s]))

(deftest simple-test
  (testing "Check with simple test"
           (is
            (= (answer1 [0, 3, 6] 2020) 436))))

(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is
            (= (answer1 [15, 5, 1, 4, 7, 0] 2020) 1259))))

;(deftest check-answer2
;  (testing "Answer part 2 is correct"
;           (is
;            (= (answer1 [15, 5, 1, 4, 7, 0] 30000000) 689)))) ;; Takes 40 seconds, could be better