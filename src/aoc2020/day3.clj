(ns aoc2020.day3
  (:gen-class))

(defn input->infinite-map [input]
  (->> input
       (.trim)
       (clojure.string/split-lines)
       (seq)
       (map cycle)
       (vec)))

(defn tree-count-at-pos [map [x y]]
  (if (= \# (nth (seq (nth map y)) x))
    1
    0))

(defn trajectory-treecount [input [slope_x slope_y]]
  (let [map   (input->infinite-map input)
        heigh (count map)]
    (loop [[posx posy] [0 0]
           treecount   0]
      (if (< posy heigh)
        (recur [(+ posx slope_x) (+ posy slope_y)] (+ treecount (tree-count-at-pos map [posx posy])))
        treecount))))

(defn slopes-tree-multiple [input slopes]
  (->> slopes
       (map #(trajectory-treecount input %))
       (apply *)))