(ns adventofcode-clj-2018.day11
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]))

(defn parse-input []
  (-> (u/input 11) cs/trim read-string))

(defn power-cell [[x y] serial]
  (let [id (+ x 10)]
    (- (rem (quot (* id (+ serial (* y id))) 100) 10) 5)))

(defn summed-area-table [coord->val]
  (reduce (fn [sums [x y]]
            (assoc sums
                      [x y]
                      (+ (coord->val [x y])
                         (get sums [(dec x) y] 0)
                         (get sums [x (dec y)] 0)
                         (- (get sums [(dec x) (dec y)] 0)))))
          {}
          (for [y (range 300),  x (range 300)] [x y])))

(defn square-sum [sums [[x y] size]]
  (let [i (dec size)]
    (+ (get sums [(+ x i) (+ y i)])
       (get sums [(dec x) (dec y)] 0)
       (- (get sums [(+ x i) (dec y)] 0))
       (- (get sums [(dec x) (+ y i)] 0)))))

(defn squares-by-size [s]
  (for [x (range (- 300 s)), y (range (- 300 s))]
    [[x y] s]))

(defn part-1 []
  (let [serial (parse-input)
        sums   (summed-area-table #(power-cell % serial))]
    (->> (squares-by-size 3)
         (map (juxt identity (partial square-sum sums)))
         (reduce (fn [[c1 s1] [c2 s2]] (if (> s1 s2) [c1 s1] [c2 s2])))
         (pop)
         (flatten)
         (butlast)
         (cs/join ","))))

(defn part-2 []
  (let [serial (parse-input)
        sums   (summed-area-table #(power-cell % serial))]
    (->> (range 1 301)
         (mapcat squares-by-size)
         (map (juxt identity (partial square-sum sums)))
         (reduce (fn [[c1 s1] [c2 s2]] (if (> s1 s2) [c1 s1] [c2 s2])))
         (pop)
         (flatten)
         (cs/join ","))))
