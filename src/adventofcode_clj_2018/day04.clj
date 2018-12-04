(ns adventofcode-clj-2018.day04
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]))

(defn parse-line [line]
  (let [words            (cs/split line #" ")
        [year month day] (mapv u/parse-int
                               (cs/split (cs/join (rest (first words))) #"-"))
        [hour minute]    (mapv u/parse-int
                               (cs/split (cs/join (butlast (second words))) #":"))]
    {:time [year month day hour minute]
     :inst (if (= 6 (count words)) (u/parse-int (nth words 3)) :action)}))

(defn guards-events [events acc]
  (if (empty? events)
    acc
    (let [guard   (:inst (first events))
          gevents (take-while (comp not number? :inst) (rest events))]
      (recur (drop-while (comp not number? :inst) (rest events))
             (update acc guard #(concat % gevents))))))

(defn inc-time [[year month day hour minute]]
  (cond (and (= 23 hour) (= minute 59)) [year month (inc day) 0 0]
        (= minute 59)                   [year month day (inc hour) 0]
        :else                           [year month day hour (inc minute)]))

(defn minute-asleep [s a]
  (->> (iterate (fn [[_ ms s]] (if (= a s) [true ms] [false (conj ms s) (inc-time s)]))
                [false [] s])
       (drop-while (comp not first))
       (first)
       (second)
       (map last)))

(defn time-asleep [guard-events]
  (->> guard-events
       (map :time)
       (partition 2)
       (mapcat (partial apply minute-asleep))))

(defn parse-input [input]
  (let [events (->> input cs/split-lines (map parse-line) (sort-by :time))
        gs     (guards-events events {})]
    (map (fn [[guard events]] [guard (time-asleep events)]) gs)))

(defn most-time-in [[_ minutes]]
  (->> minutes frequencies (sort-by second #(compare %2 %1)) first))

(defn part-1 []
  (let [chosen (->> (u/input 4) parse-input (sort-by (comp count second)) last)]
    (* (first chosen) (first (most-time-in chosen)))))

(defn part-2 []
  (let [chosen (->> (u/input 4) parse-input (sort-by (comp last most-time-in)) last)]
    (* (first chosen) (first (most-time-in chosen)))))
       
