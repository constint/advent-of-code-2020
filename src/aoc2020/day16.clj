(ns aoc2020.day16
  (:require [clojure.spec.alpha :as s]))


(defn parse-rule [rule-str]
  (let [[_ name min1 max1 min2 max2]    (re-matches #"(.+): (\d+)-(\d+) or (\d+)-(\d+)" rule-str)]
    {:name     name
     ;:min1     min1
     ;:max1     max1
     ;:min2     min2
     ;:max2     max2
     :check-fn #(or
                 (<= (Integer/parseInt min1) % (Integer/parseInt max1))
                 (<= (Integer/parseInt min2) % (Integer/parseInt max2)))}))

(defn parse-ticket [ticket-str]
  (map #(Integer/parseInt %) (clojure.string/split ticket-str #",")))

(defn parse [input]
  (let [[rule-entries _ my-ticket-entries _ nearby-ticket-entries]  (->> input
                                                                         .trim
                                                                         (clojure.string/split-lines)
                                                                         (partition-by empty?))]
    {:rules          (map parse-rule rule-entries)
     :my-ticket      (parse-ticket (nth my-ticket-entries 1))
     :nearby-tickets (map parse-ticket (drop 1 nearby-ticket-entries))}))


(defn invalid-any-rule? [rules value]
  (let [check-fns (map :check-fn rules)]
    (every? false? (map #(% value) check-fns))))

(defn filter-invalid [rules values]
  (let [check-fn (partial invalid-any-rule? rules)]
    (filter check-fn values)))

(defn answer1 [input]
  (let [{:keys [rules nearby-tickets]} (parse input)
        invalid-values                 (filter-invalid rules (flatten nearby-tickets))]
    (reduce + invalid-values)))

;; PART 2

(defn tickets->values-at-same-indices [tickets]
  (for [i (range 0 (count (first tickets)))]
    (map #(nth % i) tickets)))

(defn rule-valid-indices [values-at-same-indices check-fn]
  (filter some?
          (map-indexed
           (fn [index values]
             (if (every? check-fn values)
               index
               nil))
           values-at-same-indices)))

(defn rules-valid-indices [rules valid-tickets]
  (let [values-at-same-indices           (tickets->values-at-same-indices valid-tickets)]
    (for [{:keys [name check-fn]} rules]
      {:name          name
       :valid-indices (rule-valid-indices values-at-same-indices check-fn)})))

(defn rules-indices [rules-valid-indices]
  (let [sorted           (sort-by (comp count :valid-indices) rules-valid-indices)] ;; Sort by rule with the least possible valid indices
    (reduce
     (fn [rulemap {:keys [name valid-indices]}]
       (assoc rulemap
              (first (clojure.set/difference (set valid-indices) (set (keys rulemap)))) ;; Assume there will be only one possible valid index once we exclude the ones already chosen
              name))
     {} sorted)))

(defn tag-ticket-values [ticket rule-indices]
  (map (fn [ticket-val [_ name]] {:name name :value ticket-val})
       ticket (sort-by first rule-indices)))

(defn answer2 [input]
  (let [{:keys [rules nearby-tickets my-ticket]}       (parse input)
        valid-tickets                                  (->> nearby-tickets
                                                            (filter #(empty? (filter-invalid rules %))))
        rule-valid-indices                             (rules-valid-indices rules valid-tickets)
        rule-indices                                   (rules-indices rule-valid-indices)
        my-ticket-details                              (tag-ticket-values my-ticket rule-indices)
        departure-details                              (filter #(.startsWith (:name %) "departure") my-ticket-details)]
    (reduce * (map :value departure-details))))