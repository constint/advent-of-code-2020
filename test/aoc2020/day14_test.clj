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
  (testing "Answer part 1 is correct"
           (is
            (= (input->answer1 input) 8471403462063))))

(deftest masking-test-v2
  (testing "Check with simple test"
           (is
            (= (apply-mask-v2 "000000000000000000000000000000X1001X" 42) "000000000000000000000000000000X1101X"))
           (is
            (= (apply-mask-v2 "00000000000000000000000000000000X0XX" 26) "00000000000000000000000000000001X0XX"))))

(deftest address-expansion
  (testing "Check with simple test"
           (is
            (= (map bitseq->int (expand-address (seq "X1101X")))
               '(26 27 58 59)))
           (is
            (= (map bitseq->int (expand-address (seq "1X0XX")))
               '(16 17 18 19 24 25 26 27)))))


(deftest v2-test-input
  (testing "Check simple v2"
           (is
             (=
               (input->answer2 "mask = 000000000000000000000000000000X1001X
mem[42] = 100
mask = 00000000000000000000000000000000X0XX
mem[26] = 1")
               208))))

(deftest check-answer2
  (testing "Answer part 2 is correct"
           (is
            (= (input->answer2 input) 2667858637669))))