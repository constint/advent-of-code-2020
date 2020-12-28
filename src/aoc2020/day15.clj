(ns aoc2020.day15
  (:require [clojure.spec.alpha :as s]))

(defn cons-and-keep-2
  [list turn]
  (take 2
        (cons turn list)))

(defn compute-next-step [struct previous turn]
  (let [prev-turns  (get struct previous)]
    (if (or (nil? prev-turns) (= (count prev-turns) 1)) ;; Spoken once (last turn)
      [(update-in struct [0] cons-and-keep-2 turn) 0]
      (let [newnum                           (- (first prev-turns) (second prev-turns))]
        [(update-in struct [newnum] cons-and-keep-2 turn) newnum]))))

(defn answer1 [inputnums stopat]
  (let [starting-struct (into {} (map-indexed (fn [idx num] [num [(+ idx 1)]]) inputnums))
        starting-turn   (+ (count inputnums) 1)
        turn-stop       (+ stopat 1)]
    (loop [struct   starting-struct
           previous (last inputnums)
           turn     starting-turn]
      (if    (= turn turn-stop)
        previous
        (let [[newstruct current] (compute-next-step struct previous turn)]
          (recur newstruct current (+ turn 1)))))))
