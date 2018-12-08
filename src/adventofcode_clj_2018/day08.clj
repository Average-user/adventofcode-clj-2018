(ns adventofcode-clj-2018.day08
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]))

(declare parse-data)

(defn parse-childrens [acc xs i]
  (->> [acc xs]
       (iterate (fn [[acc xs]] (let [[n rst] (trampoline parse-data xs)]
                                 [(conj acc n) rst])))
       (drop i)
       (first)))

(defn parse-data [[cs ms & rst]]
  (let [[children xs]  (parse-childrens [] rst cs)
        [metadata xs'] (split-at ms xs)]
    [{:children children, :metadata metadata} xs']))

(defn extract-metadata [{:keys [children metadata]}]
  (flatten (concat metadata (map extract-metadata children))))

(defn part-1 []
  (->> (cs/split (u/input 8) #" ")
       (map u/parse-int)
       (parse-data)
       (first)
       (extract-metadata)
       (reduce +)))

(defn node-value [{:keys [children metadata]}]
  (if (empty? children)
    (reduce + metadata)
    (->> metadata
         (keep #(first (drop (dec %) children)))
         (map node-value)
         (reduce +))))
  
(defn part-2 []
  (->> (cs/split (u/input 8) #" ")
       (map u/parse-int)
       (parse-data)
       (first)
       (node-value)))
