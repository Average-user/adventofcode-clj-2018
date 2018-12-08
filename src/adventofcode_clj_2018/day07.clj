(ns adventofcode-clj-2018.day07
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]
            [clojure.core.match :as m]
            [clojure.set :as s]))

(defn parse-input []
  (letfn [(parse-line-words [ws] [(second ws) (nth ws 7)])]
    (map #(parse-line-words (cs/split % #" ")) (cs/split-lines (u/input 7)))))

(defn sort-by-priority [ps letters]
  (loop [began #{}, acc []]
    (let [dependent (fn [l] (map first (filter #(= (second %) l) ps)))
          chosen    (first (sort (filter (fn [l] (->> l dependent (every? began))) 
                                         (filter (comp not began) letters))))]

      (if (nil? chosen) acc (recur (conj began chosen) (conj acc chosen))))))

(defn part-1 []
  (let [input   (parse-input)
        letters (set (apply concat input))]
   (cs/join (sort-by-priority input letters))))

(def letter-time
  (into {} (map vector (map str "ABCDEFGHIJKLMNOPQRSTUVWXYZ")
                (range 61 Double/POSITIVE_INFINITY))))

(defn dec* [x] (if (<= x 0) 0 (dec x)))

(defn add-letter [letter workers]
  (loop [ws workers, acc []]
    (m/match (first ws)
             [i 0 nil]  (reduce conj acc (cons [i (letter-time letter) letter] (rest ws)))
             nil        acc
             _          (recur (rest ws) (conj acc (first ws))))))

(defn add-letters [letters workers]
  (let [nils (count (filter (complement peek) workers))]
    (reduce #(add-letter %2 %1) workers (take nils (sort letters)))))

(defn remove-letter [letter workers]
  (let [[i _ _] (first (filter #(= letter (peek %)) workers))]
    (conj (filterv #(not= letter (peek %)) workers)
          [i 0 nil])))

(defn calculate-time [ps letters]
  (loop [fins    #{}
         workers (mapv vector (range 5) (repeat 0) (repeat nil))
         acc     []]
    (let  [dependent (fn [l] (map first (filter #(= (second %) l) ps)))
           options   (sort (filter (fn [l] (->> l dependent (every? fins))) 
                                   (filter (comp not fins) letters)))
           finished  (set (keep (fn [[_ s l]] (when (and l (= 1 s)) l)) workers))
           working   (set (keep peek workers))
           nops      (filter (complement working) options)
           nws       (map (fn [[w s l]] [w (dec* s) l]) workers)
           nws'      (reduce #(remove-letter %2 %1) nws finished)
           ffins     (s/union fins finished)]
      (if (empty? options)
        acc
        (let [nws'' (add-letters nops nws')]
          (recur ffins nws'' (conj acc workers)))))))
           
(defn part-2 []
  (let [input   (parse-input)
        letters (set (apply concat input))]
    (->> (calculate-time input letters)
         (filter (partial some peek))
         (count)
         (- 5)
         (Math/abs))))
    
