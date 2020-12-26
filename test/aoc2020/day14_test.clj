(ns aoc2020.day14-test
  (:require [clojure.test :refer :all]
            [aoc2020.day14 :refer :all]
            [clojure.spec.alpha :as s]))

(deftest masking-test
  (testing "Check with simple test"
           (is
            (= (apply-mask "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X" 11) 73))
           (is
            (= (apply-mask "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X" 101) 101))
           (is
            (= (apply-mask "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X" 0) 64))))




(run-tests *ns*)