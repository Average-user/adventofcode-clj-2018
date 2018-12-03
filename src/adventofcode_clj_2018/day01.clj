(ns adventofcode-clj-2018.day01
  (:require [adventofcode-clj-2018.util :as u]))

(defn part-1 []
  (reduce + (map read-string (clojure.string/split (u/input 1) #"\n"))))

(defn part-2 []
  (->> (iterate (fn [[[x & xs] seen n]]
                  (if (seen (+ x n))
                    (+ x n)
                    [xs (conj seen (+ x n)) (+ x n)]))
         [(cycle (map read-string (clojure.string/split (u/input 1) #"\n"))) #{0} 0])
       (drop-while vector?)
       (first)))
