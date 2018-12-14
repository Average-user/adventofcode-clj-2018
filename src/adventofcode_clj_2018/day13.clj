(ns adventofcode-clj-2018.day13
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]))

(def opp {:left :straight, :straight :right, :right :left})

(def original
  (let [i  (-> "resources/day13.txt" slurp cs/split-lines)
        rs (reduce max (map count i))
        f  #(case % \> \-, \< \-, \^ \|, \v \| %)]
    (->> (for [x (range (count i)) y (range rs)]
           [[x y] (f (get-in i [x y]))])
         (filter #(#{\- \| \\ \/ \+} (second %)))
         (into {}))))

(defn parse-input []
  (let [i  (-> "resources/day13.txt" slurp cs/split-lines)
        rs (reduce max (map count i))
        f  #(if (nil? %) \space %)]
    (->> (for [x (range (count i)) y (range rs)]
           [[x y] [(f (get-in i [x y])) :left]])
         (filter #(#{ \> \< \^ \v} (first (second %))))
         (into {}))))

(defn to-move-in-order [carts]
  (->> carts
       (sort-by ffirst)
       (partition-by ffirst)
       (mapcat (partial sort))))
       
(def next-coord
  {\> [0 1]
   \< [0 -1]
   \^ [-1 0]
   \v [1 0]})

(def turn
  {[\> \\] \v
   [\< \\] \^
   [\^ \\] \<
   [\v \\] \>
   [\> \/] \^
   [\< \/] \v
   [\^ \/] \>
   [\v \/] \<})

(defn turn* [[dir pred]]
  (case [dir pred]
    [\> :right]     [\v [+1 0]]
    [\< :right]     [\^ [-1 0]]
    [\^ :right]     [\> [0 +1]]
    [\v :right]     [\< [0 -1]]
    [\>  :left]     [\^ [-1 0]]
    [\<  :left]     [\v [+1 0]]
    [\^  :left]     [\< [0 -1]]
    [\v  :left]     [\> [0 +1]]
    [dir (next-coord dir)]))

(defn move [carts [x y] [dir pred]]
  (let [[ncoord ndir npred]
        (case (original [x y])
          \+ (let [[ndir nc] (turn* [dir pred])]
               [(mapv + [x y] nc) ndir (opp pred)])
          \\ (let [ndir (turn [dir \\])]
               [(mapv + [x y] (next-coord ndir)) ndir pred])
          \/ (let [ndir (turn [dir \/])]
               [(mapv + [x y] (next-coord ndir)) ndir pred])
          [(mapv + [x y] (next-coord dir)) dir pred])]
    (if-not (nil? (carts ncoord))
      [:crash ncoord nil]
      [ncoord ndir npred]))) 

(defn apply-move [carts [x y] [ncoord ndir npred]]
  (-> carts
      (dissoc [x y])
      (assoc ncoord [ndir npred])))
       
(defn tick [part1 carts]
  (loop [[t & ts] (to-move-in-order carts), cs carts]
    (if (nil? t)
      cs
      (let [[coord val] t
            [a b c]     (move cs coord val)]
        (cond
          (nil? (cs coord)) (recur ts cs)
          (= :crash a)      (if part1 [a b c] (recur ts (reduce dissoc cs [b coord])))
          :else             (recur ts (apply-move cs coord [a b c])))))))
          
(defn part-1 []
  (->> (parse-input)
       (iterate (partial tick true))
       (drop-while (comp not vector?))
       (first)
       (second)
       (reverse)
       (cs/join ",")))

(defn part-2 []
  (->> (parse-input)
       (iterate (partial tick false))
       (drop-while #(> (count %) 1))
       (first)
       (keys)
       (first)
       (reverse)
       (cs/join ",")))
