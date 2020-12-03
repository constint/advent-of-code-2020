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
           (is (= (trajectory-treecount test-input [3 1]) 7))))


(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is (= (trajectory-treecount input [3 1]) 211))))


(deftest check-answer2
  (testing "Answer part 2 is correct"
           (is
            (=
             (slopes-tree-multiple input
                                   [[1 1] [3 1] [5 1] [7 1] [1 2]])
             3584591857))))