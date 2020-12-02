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

(defn valid-password-policy2? [[pos1 pos2 letter password]]
  (let [searched-char  (first (seq letter))
        password-chars (seq password)
        pos1-char      (nth password-chars (- pos1 1))
        pos2-char      (nth password-chars (- pos2 1))]
    (if (= pos1-char searched-char)
      (not= pos2-char searched-char)
      (= pos2-char searched-char))))

(defn answer2 [input]
  (->> input
       (input->rows)
       (filter valid-password-policy2?)
       (count)))
