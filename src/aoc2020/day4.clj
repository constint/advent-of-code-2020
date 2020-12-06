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

(s/def ::passport-v1
       (s/keys :req-un [:v1/byr :v1/iyr :v1/eyr :v1/hgt :v1/hcl :v1/ecl :v1/pid]
               :opt-un [:v1/cid]))

(defn valid-passport-count [spec input]
  (->> (input->passport-lines input)
       (map parse-passport)
       (filter (partial s/valid? spec))
       (count)))

(defn valid-passport-count-v1 [input]
  (valid-passport-count ::passport-v1 input))

(defn valid-passport-count-v2 [input]
  (valid-passport-count ::passport-v2 input))

(s/def ::passport-v2
       (s/keys :req-un [:v2/byr :v2/iyr :v2/eyr :v2/hgt :v2/hcl :v2/ecl :v2/pid]
               :opt-un [:v2/cid]))

(defn str->int [x]
  (if (string? x)
    (try
      (Integer/parseInt x)
      (catch Exception _
        :clojure.spec/invalid))))

(s/def ::integer-string (comp integer? str->int))

(defn custom-string-spec [length lower-bound upper-bound]
  (s/and ::integer-string
         #(= (.length %) length)
         #(<= lower-bound (str->int %) upper-bound)))

(s/def :v2/byr (custom-string-spec 4 1920 2002))
(s/def :v2/iyr (custom-string-spec 4 2010 2020))
(s/def :v2/eyr (custom-string-spec 4 2020 2030))

(s/def :v2/metric-hgt
       (s/and
        #(re-matches #"(\d+)cm" %)
        #(<= 150 (str->int (second (re-matches #"(\d+)cm" %))) 193)))

(s/def :v2/imperial-hgt
       (s/and
        #(re-matches #"(\d+)in" %)
        #(<= 59 (str->int (second (re-matches #"(\d+)in" %))) 76)))

(s/def :v2/hgt
       (s/or :metric-hgt :v2/metric-hgt :imperial-hgt :v2/imperial-hgt))

(s/def :v2/hcl
       (s/and
        #(re-matches #"\#[0-9a-f]+" %)
        #(= (.length %) 7)))

(s/def :v2/ecl #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})

(s/def :v2/pid
       (s/and
        ::integer-string
        #(= (.length %) 9)))
