(ns adventofcode-clj-2018.day14
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]))

(defn update-recipes [[rs l r] n]
  (let [l-move (inc (nth rs l))
        r-move (inc (nth rs r))
        sum    (+ (nth rs l) (nth rs r))
        rs'    (if (<= 10 sum)
                 (into rs [(quot sum 10) (rem sum 10)])
                 (conj rs sum))]
    [rs' (rem (+ l l-move) (count rs')) (rem (+ r r-move) (count rs'))]))

(defn part-1 []
  (let [input (read-string (u/input 14))]
    (->> (range input)
         (reduce update-recipes [[3 7] 0 1])
         (first)
         (drop input)
         (take 10)
         (cs/join)
         (read-string))))

(defn solve-part-2 [input]
  (loop [rs [3 7], l 0, r 1]
    (let [l-move (inc (nth rs l))
          r-move (inc (nth rs r))
          sum    (+ (nth rs l) (nth rs r))
          rs'    (if (<= 10 sum)
                   (into rs [(quot sum 10) (rem sum 10)])
                   (conj rs sum))
          rsc    (count rs')
          cond1  (and (<= 6 rsc) (= input (subvec rs' (- rsc 6))))
          cond2  (and (<= 7 rsc) (= input (subvec rs' (- rsc 7) (+ 6 (- rsc 7)))))]
      (cond cond1 (subvec rs' 0 (- rsc 6))
            cond2 (subvec rs' 0 (- rsc 7))
            :esle (recur rs' (rem (+ l l-move) rsc) (rem (+ r r-move) rsc))))))

(defn part-2 []
  (let [input (mapv #(Character/getNumericValue %) (cs/trim (u/input 14)))]
    (count (solve-part-2 input))))
