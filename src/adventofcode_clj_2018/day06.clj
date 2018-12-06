(ns adventofcode-clj-2018.day06
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]))

(defn parse-input []
  (->> 6 u/input cs/split-lines (map #(mapv u/parse-int (cs/split % #",")))))

(defn mdistance [[a b] [c d]]
  (+ (Math/abs (- a c)) (Math/abs (- b d))))

(defn closest-point [p points]
  (let [x (->> points
               (map #(vector % (mdistance p %)))
               (sort-by second)
               (partition-by second)
               (first)
               (map first))]
    (when (= (count x) 1)
      (first x))))

(defn margin [minx maxx miny maxy]
 (set (concat (map vector (repeat minx) (range (inc maxy)))
              (map vector (range (inc maxx)) (repeat miny))
              (map vector (repeat maxx) (range (inc maxy)))
              (map vector (range (inc maxx)) (repeat maxy)))))

(defn infinite-areas [points margin]
  (set (map #(closest-point % points) margin)))

(defn part-1 []
  (let [input       (parse-input)
        [minx maxx] ((juxt #(reduce min %) #(reduce max %)) (map first input))
        [miny maxy] ((juxt #(reduce min %) #(reduce max %)) (map second input))
        infareas    (infinite-areas input (margin minx maxx miny maxy))
        ps]
    (->> (for [x (range minx (inc maxx)) y (range miny (inc maxy))]
           (let [c (closest-point [x y] input)]
             (when (and c (not (contains? infareas c)))
               c)))
         (filter identity)
         (sort)
         (partition-by identity)
         (map count)
         (reduce max))))

(defn part-2 []
  (let [input       (parse-input)
        [minx maxx] ((juxt #(reduce min %) #(reduce max %)) (map first input))
        [miny maxy] ((juxt #(reduce min %) #(reduce max %)) (map second input))
        total       #(reduce + (map (partial mdistance %) input))]
    (->> (for [x (range minx (inc maxx)) y (range miny (inc maxy))]
           (total [x y]))
         (filter #(< % 10000))
         (count))))
