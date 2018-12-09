(ns adventofcode-clj-2018.day09
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]
            [clojure.data.finger-tree :as f]))

(defn parse-input []
  (let [input (cs/split (slurp "resources/day09.txt") #" ")]
    [(read-string (first input)) (read-string (get input 6))]))

(defn splitplace [n]
  (case n
    0 0
    1 1
    2 0
    2))

(defn game [players marbles]
  (loop [scores (into {} (map vector (range players) (repeat 0)))
         circle (f/counted-double-list 0)
         player 0
         marble 1]
    (let [np (rem (inc player) players)]
      (cond
        (> marble marbles)      (reduce max (map second scores))
        (zero? (rem marble 23)) (let [[l c cs] (f/ft-split-at circle (- (count circle) 7))]
                                  (recur (update scores player #(+ marble c %))
                                         (f/ft-concat cs l)
                                         np
                                         (inc marble)))
        :else (let [[l c cs] (f/ft-split-at circle (splitplace (count circle)))
                    cs'      (if (nil? c) cs (f/conjl cs c))]
                (recur scores
                       (f/conjl (f/ft-concat cs' l) marble)
                       np
                       (inc marble)))))))
                                       
(defn part-1 []
  (let [[players marbles] (parse-input)]
    (game players marbles)))

(defn part-2 []
  (let [[players marbles] (parse-input)]
    (game players (* 100 marbles))))
