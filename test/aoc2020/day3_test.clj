(ns aoc2020.day3-test
  (:require [clojure.test :refer :all]
            [aoc2020.day3 :refer :all]))

(def input (slurp "resources/day3.txt"))

(def test-input
  "..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#")


(defn printmap [map width]
  (doseq [line map]
    (print (apply str (take width line))
           "\n")))

(deftest example
  (testing "Test example"
           (is (= (trajectory-treecount test-input) 7))))

(run-all-tests)

(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is (= (trajectory-treecount input) 211))))

;(deftest check-answer2
;  (testing "Answer part 2 is correct"
;           (is (= (answer2 input) 690))))