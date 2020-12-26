(ns aoc2020.day14
  (:require [clojure.spec.alpha :as s]))

(defn bit->int [idx bit-char]
  (if (= bit-char \1)
    (long (Math/pow 2 idx))
    0))

(defn bitstring->int [str]
  (reduce + (map-indexed bit->int (reverse str))))

(defn or-mask
  "Or mask to turn bits to 1"
  [string]
  (->
   (clojure.string/replace string #"X" "0")
   bitstring->int))

(defn and-mask
  "And mask to turn bits to 0"
  [string]
  (->
   (clojure.string/replace string #"X" "1")
   bitstring->int))

(defn apply-mask [mask value]
  (-> value
      (bit-or (or-mask mask))
      (bit-and (and-mask mask))))
