(ns aoc2020.day5-test
  (:require [clojure.test :refer :all]
            [aoc2020.day5 :refer :all]
            [clojure.spec.alpha :as s]))

(def input (slurp "resources/day5.txt"))

(deftest row
  (testing "Row parsing"
           (is (= (reduce reduce-range-fn [0 7] (seq "RLR")) [5 5]))
           (is (= (reduce reduce-range-fn [0 127] (seq "FBFBBFF")) [44 44]))))

(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is (= (max-seat-id input) 894))))

(deftest check-answer2
  (testing "Answer part 2 is correct"
           (is (= (input->free-id input) 579))))
