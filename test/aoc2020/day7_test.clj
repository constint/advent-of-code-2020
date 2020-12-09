(ns aoc2020.day7-test
  (:require [clojure.test :refer :all]
            [aoc2020.day7 :refer :all]
            [clojure.spec.alpha :as s]))

(def input (slurp "resources/day7.txt"))

(def testinput
  "light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.")

(deftest simple-test
  (testing "Check with simple test"
           (is (= (count (possible-containers testinput "shiny gold bag")) 4))))

(deftest check-answer1
  (testing "Answer part 1 is correct"
           (is (= (count (possible-containers input "shiny gold bag")) 164))))
