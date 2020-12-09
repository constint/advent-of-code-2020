(ns aoc2020.day7
  (:require [clojure.spec.alpha :as s]))

(defn parse-input-line
  "Quick and dirty transform to edn form [bag1 [bag2 bag3 bag4]]"
  [input]
  (let [preprocessed                   (-> input
                                           (clojure.string/replace #"bags" "bag")
                                           (clojure.string/replace #"\." "")
                                           (clojure.string/replace #"\d+ " ""))
        [_ container contained-string] (re-matches #"(.+) contain (.+)" preprocessed)]
    [container (clojure.string/split contained-string #", ")]))

(defn input->bag-map [input]
  (->> input
       (.trim)
       (clojure.string/split-lines)
       (map parse-input-line)
       (into {})))

(defn can-contain-bag? [bagmap searched-bag container-bar]
  (loop [visited  #{}
         to-visit #{container-bar}]
    (if (contains? to-visit searched-bag)
      true
      (let [to-visit-new (clojure.set/difference (set (mapcat bagmap to-visit)) visited)]
        (if (empty? to-visit-new)
          false
          (recur (into visited to-visit) to-visit-new))))))

(defn possible-containers [input bag]
  (let [bagmap (input->bag-map input)
        bagmap-without-target (dissoc bagmap bag)
        contains-shiny-gold? (partial can-contain-bag? bagmap-without-target bag)]
    (filter contains-shiny-gold? (keys bagmap-without-target))))
