(ns aoc2020.day2
  (:gen-class))

(defn parse-row [row]
  (let [[_ lower_bound upper_bound letter password] (re-matches #"(\d+)-(\d+) (.): (.+)" row)]
    [(Integer/parseInt lower_bound) (Integer/parseInt upper_bound) letter password]))

(defn input->rows [input]
  (->> input
       (clojure.string/split-lines)
       (map parse-row)))

(defn valid-password? [[lower_bound upper_bound letter password]]
  (let [searched-char (first (seq letter))
        char-count    (count (filter #{searched-char} (seq password)))]
    (<= lower_bound char-count upper_bound)))

(defn answer1 [input]
  (->> input
       (input->rows)
       (filter valid-password?)
       (count)))


(defn answer2 [input]
  (apply * (first (triples-that-sums-to-2020 input))))