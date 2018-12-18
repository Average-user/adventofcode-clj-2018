(ns adventofcode-clj-2018.day18
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]
            [clojure.set :as s]))

(defn parse-input []
  (let [input (cs/split-lines (u/input 18))]
    (into {} (for [x (range (count input)) y (range (count input))]
               [[x y] (get-in input [x y])])))) 

(defn neighbors [acres [x y]]
  (keep acres (mapv (partial mapv + [x y])
                    [[0 1] [1 0] [0 -1] [-1 0] [1 1] [1 -1] [-1 1] [-1 -1]])))

(defn update-acre [acres acre type]
  (let [ns (neighbors acres acre)]
    (case type
      \. (if (>= (count (filter (partial = \|) ns)) 3) \| \.)
      \| (if (>= (count (filter (partial = \#) ns)) 3) \# \|)
      \# (if (s/subset? #{\# \|} (set ns))             \# \.))))
                    
(defn update-acres [acres]
  (reduce #(update %1 %2 (partial update-acre acres %2)) acres (map first acres)))

(defn score [acres]
  (let [types (map second acres)]
    (* (count (filter (partial = \|) types))
       (count (filter (partial = \#) types)))))

(defn part-1 []
  (->> (parse-input)
       (iterate update-acres)
       (drop 10)
       (first)
       (score)))

(defn find-cycle [acres]
  (loop [acres acres, seen #{}, seq []]
    (if (seen acres)
      [(drop-while (partial not= acres) seq) (count seq)]
      (recur (update-acres acres) (conj seen acres) (conj seq acres)))))
  
(defn part-2 []
  (let [[cycle i] (find-cycle (parse-input))]
    (score (nth cycle (rem (- 1000000000 i) (count cycle))))))
    
  
