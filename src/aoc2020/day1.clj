(ns aoc2020.day1
  (:gen-class))

(defn pairs-that-sum-to-2020 [input]
  (filter some?
          (for [i input
                j input]
            (if (and
                 (< i j)
                 (= (+ i j) 2020))
              [i j]))))

(defn answer1 [input]
  (apply * (first (pairs-that-sum-to-2020 input))))


(defn triples-that-sum-to-2020 [input]
  (filter some?
          (for [i input
                j input
                k input]
            (if (and
                 (< i j k)
                 (= (+ i j k) 2020))
              [i j k]))))

(defn answer2 [input]
  (apply * (first (triples-that-sum-to-2020 input))))