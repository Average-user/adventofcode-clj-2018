(ns adventofcode-clj-2018.day07
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]))

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
  (cons [(letter-time letter) letter]
        (rest (sort-by second workers))))

(defn add-letters [letters workers]
  (let [nils (count (filter (complement peek) workers))]
    (reduce #(add-letter %2 %1) workers (take nils (sort letters)))))

(defn remove-letter [letter workers]
  (conj (filterv #(not= letter (peek %)) workers)
        [0 nil]))

(defn calculate-time [ps letters]
  (loop [fins    #{}
         workers (mapv vector (take 5 (repeat 0)) (repeat nil))
         acc     []]
    (let [workers'  (map (fn [[s l]] [(dec* s) l]) workers)
          finished  (map second (filter (fn [[s l]] (and l (= 0 s))) workers'))
          fins'     (reduce conj fins finished)
          dependent (fn [l] (map first (filter #(= (second %) l) ps)))
          options   (sort (filter (fn [l] (->> l dependent (every? fins'))) 
                                  (filter (comp not fins') letters)))
          reduced   (reduce #(remove-letter %2 %1) workers' finished)
          working   (set (keep peek workers))
          options'  (filter (complement working) options)
          additions (add-letters options' reduced)]
      (if (empty? options)
        acc
        (recur fins' additions (conj acc additions))))))
         
(defn part-2 []
  (let [input   (parse-input)
        letters (set (apply concat input))]
    (->> (calculate-time input letters)
         (filter (partial some peek))
         (count))))
