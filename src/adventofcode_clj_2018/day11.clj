(ns adventofcode-clj-2018.day11
  (:require [adventofcode-clj-2018.util :as u]))

(defn parse-input []
  (-> (u/input 11) cs/trim read-string))

(defn power-cell [[x y] serial]
  (let [id (+ x 10)]
    (- (rem (quot (* id (+ serial (* y id))) 100) 10) 5)))

(defn in? [[x y] [[x1 y1] [x2 y2]]]
  (and (<= x1 x x2) (<= y1 y y2)))

(defn squares [f g [x y]]
  (letfn [(square [[x y] size]
            (reduce + (for [x' (range x (+ x size)) y' (range y (+ y size))]
                        (f [x' y']))))]
    (map (fn [s] [s [x y] (square [x y] s)])
         (g (range 1 (- 301 (max x y)))))))

(defn scored-squares [serial f]
  (->> (for [x (range 1 301), y (range 1 301)] [x y])
       (mapcat (fn [coord] (squares #(power-cell % serial) f coord)))
       (reduce (fn [[s1 x1 p1] [s2 x2 p2]] (if (> p1 p2) [s1 x1 p1] [s2 x2 p2])))))

(defn part-1 []
  (->> (scored-squares (parse-input) #(filter (partial = 3) %))
       (second)
       (cs/join ",")))

(defn part-2 []
  (let [[s [x y]] (scored-squares (parse-input) #(filter (partial > 15) %))]
    (cs/join "," [x y s])))
