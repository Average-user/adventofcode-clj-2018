(ns adventofcode-clj-2018.day02
  (:require [adventofcode-clj-2018.util :as u]))

(defn check-box [str]
  (let [fs (map second (frequencies str))]
    [(some (partial = 2) fs) (some (partial = 3) fs)]))

(defn part-1 []
  (as-> (map check-box (clojure.string/split (u/input 2) #"\n")) $
    (* (count (filter first $)) (count (filter second $)))))
  
(defn part-2 []
  (let [input (clojure.string/split (u/input 2) #"\n")]
    (->> (for [a input, b input]
           [(= 1 (count (filter identity (map (partial not=) a b)))) a b])
         (filter first)
         (first)
         (rest)
         (apply (partial map vector))
         (filter (fn [[a b]] (= a b)))
         (map first)
         (apply str))))
