(ns adventofcode-clj-2018.day12
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]))

(defn parse-input []
  (let [[x _ & xs] (cs/split-lines (u/input 12))]
    [(peek (cs/split x #" "))
     (into {} (map (fn [[x _ y]] [(seq x) (first (seq y))])
                   (map #(cs/split % #" ") xs)))]))

(defn shrink [pots]
  (letfn [(shrink-left [pots] (if (= '(\. \. \.) (map second (take 3 pots)))
                                (recur (rest pots)) pots))]
    (->> pots shrink-left reverse shrink-left reverse)))

(defn next-gen [pots rules]
  (->> pots
       (partition 5 1)
       (map #(let [r (rules (map second %))
                   i (first (nth % 2))]
               (if (nil? r) [i \.] [i r])))))

(defn add-margin [pots]
  (let [mini (ffirst pots)
        maxi (first (last pots))]
    (concat (map vector (range (- mini 3) mini) (repeat \.))
            (shrink pots)
            (map vector (range (inc maxi) (+ maxi 4)) (repeat \.)))))

(defn find-sum [pots]
  (reduce + (keep (fn [[i p]] (when (= \# p) i)) pots)))

(defn part-1 []
  (let [[initial rules] (parse-input)]
    (->> (map-indexed vector initial)
         (iterate #(next-gen (add-margin %) rules))
         (take 21)
         (last)
         (find-sum))))

(def trim-dots
  (comp (partial drop-while #(= \. %))
        reverse
        (partial drop-while #(= % \.))
        (partial map second)))  

(defn find-equilibrium [pots rules]
  (->> pots
       (iterate #(next-gen (add-margin %) rules))
       (map-indexed vector)
       (partition 2 1)
       (some (fn [[[i1 g1] [i2 g2]]]
               (if (= (trim-dots g1) (trim-dots g2))
                 (let [[s1 s2] [(find-sum g1) (find-sum g2)]]
                   [i1 s1 (- s2 s1)]))))))

(defn part-2 []
  (let [[initial rules] (parse-input)]
    (let [[i s d] (find-equilibrium (map-indexed vector initial) rules)]
      (+ s (* d (- 50000000000 i))))))
