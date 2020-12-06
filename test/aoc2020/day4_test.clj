(ns aoc2020.day4-test
  (:require [clojure.test :refer :all]
            [aoc2020.day4 :refer :all]
            [clojure.spec.alpha :as s]))

(def input (slurp "resources/day4.txt"))

(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is (= (valid-passport-count-v1 input) 250))))

(deftest passport-spec
  (testing "Password spec items check"
           (is (s/valid? :v2/byr "2002"))
           (is (not (s/valid? :v2/byr "2003")))

           (is (s/valid? :v2/hgt "60in"))
           (is (s/valid? :v2/hgt "190cm"))
           (is (not (s/valid? :v2/hgt "190in")))
           (is (not (s/valid? :v2/hgt "190")))
           (is (not (s/valid? :v2/hgt "190")))

           (is (s/valid? :v2/hcl "#123abc"))
           (is (not (s/valid? :v2/hcl "#123abz")))
           (is (not (s/valid? :v2/hcl "123abc")))

           (is (s/valid? :v2/ecl "brn"))
           (is (not (s/valid? :v2/ecl "wat")))

           (is (s/valid? :v2/pid "000000001"))
           (is (not (s/valid? :v2/pid "0123456789")))))

(deftest check-answer2
  (testing "Answer part 2 is correct"
           (is (= (valid-passport-count-v2 input) 158))))
