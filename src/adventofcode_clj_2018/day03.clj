(ns adventofcode-clj-2018.day03
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]))
 
(defn parse-claim [string]
  (let [ws      (cs/split string #" ")
        id      (u/parse-int (first ws))
        [x y]   (mapv u/parse-int (cs/split (nth ws 2) #","))
        [sx sy] (mapv u/parse-int (cs/split (nth ws 3) #"x"))]
    {:id id, :c1 [x y], :c2 [(+ sx x -1) (+ sy y -1)]}))

(defn claim-coords [{:keys [c1 c2]}]
  (for [x (range (first c1) (inc (first c2)))
        y (range (second c1) (inc (second c2)))]
    [x y]))

(defn overlap? [rec1 rec2]
  (let [[x y]   (:c1 rec1)
        [a b]   (:c2 rec1)
        [x1 y1] (:c1 rec2)
        [a1 b1] (:c2 rec2)]
    (not (or (< a x1) (< a1 x) (< b y1) (< b1 y)))))

(defn part-1 []
  (let [input (map parse-claim (cs/split (u/input 3) #"\n"))]
    (->> input
         (mapcat claim-coords)
         (sort)
         (partition-by identity)
         (filter #(> (count %) 1))
         (count))))

(defn part-2 []
  (let [input (map parse-claim (cs/split (u/input 3) #"\n"))]
    (->> input
         (filter #(every? (complement (partial overlap? %))
                          (remove (partial = %) input)))
         (first)
         (:id))))
         
