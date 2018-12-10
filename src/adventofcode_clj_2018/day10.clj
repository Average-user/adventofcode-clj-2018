(ns adventofcode-clj-2018.day10
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]))

(defn parse-line [l]
  (let [[_ p _ v] (cs/split l #"[<>]")
        p         (mapv read-string (cs/split p #","))
        v         (mapv read-string (cs/split v #","))]
    [p v]))

(defn parse-input []
  (->> (u/input 10) cs/split-lines (map parse-line)))

(defn step [points]
  (map (fn [[[x y] [vx vy]]] [[(+ x vx) (+ y vy)] [vx vy]]) points))

(defn bounds [[[[x y] _] & points]]
  (reduce
   (fn [[[minx miny] [maxx maxy]] [[px py] _]]
     [[(min minx px) (min miny py)]
      [(max maxx px) (max maxy py)]])
   [[x y] [x y]]
   points))

(defn area [points]
  (let [[[x1 y1] [x2 y2]] (bounds points)]
    (* (- x2 x1) (- y2 y1))))

(defn points->string [points]
  (let [setp (set (map first points))
        [[x1 y1] [x2 y2]] (bounds points)
        grid (map (fn [y] (mapv (fn [x] (if (setp [x y]) \# \space))
                                (range x1 (inc x2))))
                  (range y1 (inc y2)))]
    (mapv cs/join grid)))

(defn final-grid []
  (->> (parse-input)
       (iterate step)
       (map-indexed (fn [i ps] [i ps (area ps)]))
       (partition 2 1)
       (drop-while (fn [[[s _ a] [_ _ a']]] (> a a')))
       (ffirst)))

;; Use (map println (part-1)) to show the output
(defn part-1 []
  (->> (final-grid) second (points->string)))

(defn part-2 []
  (->> (final-grid) first))
