(ns adventofcode-clj-2018.day06
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]))

(defn parse-input []
  (->> 6 u/input cs/split-lines (map #(mapv u/parse-int (cs/split % #",")))))

(defn mdistance [[a b] [c d]]
  (+ (Math/abs ^long (- a c)) (Math/abs ^long (- b d))))

(defn closest-point [p points]
  (let [[[p1 d1] [p2 d2]]
        (->> points
             (map #(vector % (mdistance p %)))
             (sort-by second)
             (take 2))]
    (when-not (= d1 d2) p1)))

(defn infinite-areas [points minx maxx miny maxy]
    (->> (concat
          (for [x (range (inc maxx))] [x 0])
          (for [y (range (inc maxy))] [0 y])
          (for [x (range (inc maxx))] [x maxy])
          (for [y (range (inc maxy))] [maxx y]))
         (map #(first (sort-by (partial mdistance %) points)))
         (set)))

(defn boundaries [points]
  (let [[xs ys] [(map first points) (map second points)]]
    [(reduce min xs) (reduce max xs) (reduce min ys) (reduce max ys)]))

(defn part-1 []
  (let [points (parse-input)
        [minx maxx miny maxy] (boundaries points)
        infareas (infinite-areas points minx maxx miny maxy)
        coords (for [x     (range minx (inc maxx))
                     y     (range miny (inc maxy))
                     :let  [c (closest-point [x y] points)]
                     :when c]
                 c)]
    (->> infareas
         (reduce dissoc (frequencies coords))
         (map second)
         (reduce max))))
   
(defn part-2 []
  (let [points (parse-input)
        [minx maxx miny maxy] (boundaries points)
        total #(reduce + (map (partial mdistance %) points))]
    (count (for [x     (range minx (inc maxx))
                 y     (range miny (inc maxy))
                 :let  [t (total [x y])]
                 :when (< t 10000)] t))))

