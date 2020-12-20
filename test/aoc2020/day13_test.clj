(ns aoc2020.day13-test
  (:require [clojure.test :refer :all]
            [aoc2020.day13 :refer :all]
            [clojure.spec.alpha :as s]))

(def testarrival 939)
(def test-bus-ids [7, 13, 59, 31, 19])

(deftest simple-test
  (testing "Check with simple test"
           (is
            (= (answer1 testarrival test-bus-ids)
               295))))

(def arrival 1000509)
(def bus-ids
  (read-string
   (clojure.string/replace "[17,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,739,x,29,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,x,x,x,x,x,23,x,x,x,x,x,x,x,971,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,19]"
                           "x" "")))

(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is
            (= (answer1 arrival bus-ids)
               296))))

(deftest simple-test-2
  (testing
   "Check with simple test part2"
   (is
    (= (answer2 "[7,13,x,x,59,x,31,19]")
       1068781))))

(time (answer2 "[7,13,x,x,59,x,31,19]"))
(time (answer2 "[17,x,13,19]"))
(time (answer2 "[17,x,13,19]"))
(time (answer2 "[67,7,59,61]"))
(time (answer2 "[67,x,7,59,61]"))
(time (answer2 "[67,7,x,59,61]"))
(time (answer2 "[1789,37,47,1889]"))
(time (answer2bis "[1789,37,47,1889]"))
(time (answer2ter "[1789,37,47,1889]"))

;; (time (answer2ter "[17,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,739,x,29,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,x,x,x,x,x,23,x,x,x,x,x,x,x,971,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,19]" 100000000000000))

;(deftest check-answer2
;  (testing "Answer part 2 is correct"
;           (is
;            (=
;              (answser2-crt "[17,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,739,x,29,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,x,x,x,x,x,23,x,x,x,x,x,x,x,971,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,19]")
;              145117))))