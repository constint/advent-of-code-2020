(ns aoc2020.day4
  (:require [clojure.spec.alpha :as s]))

(defn input->passport-lines
  "Takes the input, and returns a collection of strings, each one representing a passport-entry"
  [input]
  (->> input
       (.trim)
       (clojure.string/split-lines)
       (partition-by empty?)
       (map #(clojure.string/join " " %))
       (filter not-empty)))

(defn parse-passport-property [property]
  (let [[key value] (clojure.string/split property #":")]
    [(keyword key) value]))

(defn parse-passport [passport-line]
  (->> (clojure.string/split passport-line #" ")
       (map parse-passport-property)
       (into {})))

(s/def ::passport
       (s/keys :req-un [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid]
               :opt-un [::cid]))

(defn passport-valid? [passport]
  (s/valid? ::passport passport))

(defn valid-password-count [input]
  (->> (input->passport-lines input)
       (map parse-passport)
       (filter passport-valid?)
       (count)))
