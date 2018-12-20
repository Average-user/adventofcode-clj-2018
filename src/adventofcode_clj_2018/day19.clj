(ns adventofcode-clj-2018.day19
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]
            [adventofcode-clj-2018.day16 :as d16]))

(defn parse-input []
  (let [[x & xs]   (cs/split-lines (u/input 19))
        parse-line (fn [s] (let [[l & ls] (cs/split s #" ")]
                             (mapv read-string (cons (str ":" l) ls))))]
    [(read-string (peek (cs/split x #" "))) (mapv parse-line xs)]))

(defn run [ipr is irs]
  (loop [rs irs ip 0]
    (let [i   (get is ip)
          rs' (assoc rs ipr ip)]
      (if i
        (let [rs'' (d16/apply-opcode rs' (first i) i)]
          (recur rs'' (inc (get rs'' ipr))))
        rs))))

(defn part-1 []
  (let [[ipr instructions] (parse-input)]
    (first (run ipr instructions [0 0 0 0 0 0]))))

(defn divisors [n]
  (filter #(zero? (rem n %)) (range 1 (inc n))))

;; At first I did what evryone is calling 'reverse engineering' for my specific input.
;; But after that I saw someone in reddit's megathread that claimed that inputs only 
;; differ in the Ip and the second number of instructions number 21 and 23, with the
;; first instruction being number 0
(defn part-2 []
  (let [[a b] (mapv #(get (get (second (parse-input)) %) 2) [21 23])]
    (reduce + (divisors (+ 10551236 (* a 22) b)))))
