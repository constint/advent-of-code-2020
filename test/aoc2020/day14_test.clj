(ns aoc2020.day14-test
  (:require [clojure.test :refer :all]
            [aoc2020.day14 :refer :all]
            [clojure.spec.alpha :as s]))

(deftest masking-test
  (testing "Check with simple test"
           (is
            (= (apply-mask "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X" 11) 73))
           (is
            (= (apply-mask "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X" 101) 101))
           (is
            (= (apply-mask "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X" 0) 64))))

(def input (slurp "resources/day14.txt"))

(deftest check-answer1
  (testing "Answer part 2 is correct"
           (is
            (= (input->answer1 input) 8471403462063))))

