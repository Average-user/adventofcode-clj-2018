(ns adventofcode-clj-2018.day09
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]
            [clojure.data.finger-tree :as f]))

(defn parse-input []
  (let [input (cs/split (u/input 9) #" ")]
    [(read-string (first input)) (read-string (get input 6))]))

(defn simulate-game [players marbles]
  (-> (fn [[scores circle] [player marble]]
        (if (zero? (rem marble 23))
          (let [[l c cs] (f/ft-split-at circle (- (count circle) 7))]
            [(update scores player #(+ marble c %)) (f/ft-concat cs l)])
          (let [[l c cs] (f/ft-split-at circle 2)
                cs'     (if (nil? c) cs (f/conjl cs c))]
            [scores (f/conjl (f/ft-concat cs' l) marble)])))
      (reduce [(vec (repeat players 0)) (f/counted-double-list 0) 0]
              (map vector (cycle (range players)) (range 1 (inc marbles))))
      (first))) 

(defn part-1 []
  (let [[players marbles] (parse-input)]
    (reduce max (simulate-game players marbles))))

(defn part-2 []
  (let [[players marbles] (parse-input)]
    (reduce max (simulate-game players (* 100 marbles)))))
