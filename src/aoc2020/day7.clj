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
  (let [bagmap                (input->bag-map input)
        bagmap-without-target (dissoc bagmap bag)
        contains-shiny-gold?  (partial can-contain-bag? bagmap-without-target bag)]
    (filter contains-shiny-gold? (keys bagmap-without-target))))

;; PART 2

(defn bag-elt->entry
  "Convert 'bag element' string ie: '2 whatever bag' into entry [2 'whatever bag']"
  [bag-elt]
  (if-not (re-matches #"no other bag" bag-elt)
    (let [[_ qty-string bag-name] (re-matches #"(\d+) (.+)" bag-elt)]
      [bag-name (Integer/parseInt qty-string)])))

(defn parse-input-line-2
  "Quick and dirty transform to edn form [bag1 {bag2: n, bag3: m, bag4: p]]"
  [input]
  (let [preprocessed                   (-> input
                                           (clojure.string/replace #"bags" "bag")
                                           (clojure.string/replace #"\." ""))
        [_ container contained-string] (re-matches #"(.+) contain (.+)" preprocessed)
        contained-elts                 (clojure.string/split contained-string #", ")
        _                              (prn "contained elts" contained-elts)
        contained-entries              (map bag-elt->entry contained-elts)]
    (prn "contained entries" contained-entries)
    [container (into {} contained-entries)]))

(defn input->bag-map2 [input]
  (->> input
       (.trim)
       (clojure.string/split-lines)
       (map parse-input-line-2)
       (into {})))

(defn count-bags [bagmap bag]
  (let [contained (get bagmap bag)] ;; get match in the map { bag multiplier, bag2 mutiplier2 }
    (apply + ;; Sum the result of processing on each bag/multiplier
           (map
            (fn [[inner-bag mult]]
              ;(prn "i" inner-bag "m" mult)
              (+ mult ;; Count the container itself as a bag
                 (* mult (count-bags bagmap inner-bag)) ;; recur on inner bag and multiply by the container qty
                 ))
            contained))))
