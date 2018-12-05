(ns adventofcode-clj-2018.day05
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]))

(defn reacts? [a b]
  (and a b (not= a b) (= (cs/upper-case a) (cs/upper-case b))))

(defn iteration [pol]
  (reduce (fn [a b] (if (reacts? b (peek a))
                      (pop a)
                      (conj a b)))
          [] pol))

(defn remove-units [str l]
  (cs/replace (cs/replace str (re-pattern l) "")
    (re-pattern (cs/capitalize l)) ""))

(defn part-1 []
  (let [input (cs/trim (u/input 5))]
    (count (iteration input))))

(defn part-2 []
  (let [input (cs/trim (u/input 5))]
    (->> "abcdefghijklmnopqrstuvwxyz"
         (map (comp count iteration (partial remove-units input) str))
         (reduce min))))
