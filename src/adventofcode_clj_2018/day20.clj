(ns adventofcode-clj-2018.day20
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]))

(defn clean-input []
  (->> (u/input 20) (filter #{\( \) \| \N \S \W \E})))

(defn move [dir [x y]]
  (case dir
    \N [x (dec y)]
    \E [(inc x) y]
    \S [x (inc y)]
    \W [(dec x) y]))

(defn build-graph' [stack ns es p xs]
  (if (empty? xs)
    [(map second ns) es]
    (case (first xs)
      \( (recur (cons p stack) ns es p             (rest xs))
      \| (recur stack          ns es (first stack) (rest xs))
      \) (recur (rest stack)   ns es (first stack) (rest xs))
      (let [l     (count ns)
            p'    (move (first xs) p)
            v'    (get ns p')
            ns'   (if (nil? v') (assoc ns p' l) ns)
            v     (if (nil? v') l v')]
        (recur stack ns' (conj es [(ns p) v]) p' (rest xs)))))) 

(defn build-graph [input]
  (let [[nodes edges] (build-graph' '() {[0 0] 0} #{} [0 0] input)]
    (into {} (map (fn [n] [n (keep (fn [[a b]] (when (= n a) b)) edges)]) nodes))))

(defn nodes-depth [node neighbors]
  (letfn [(options [seen n] (remove seen (neighbors n)))]
    (loop [front (options #{node} node), seen #{node}, ds [0],  depth 1]
      (if (empty? front)
        ds
        (recur (mapcat (partial options seen) front)
               (reduce conj seen front)
               (reduce conj ds (map (constantly depth) front))
               (inc depth))))))

(defn part-1 []
  (->> (clean-input) build-graph (nodes-depth 0) (reduce max)))

(defn part-2 []
  (->> (clean-input) build-graph (nodes-depth 0) (filter (partial <= 1000)) count))
