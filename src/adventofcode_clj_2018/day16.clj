(ns adventofcode-clj-2018.day16
  (:require [adventofcode-clj-2018.util :as u]
            [clojure.string :as cs]
            [clojure.set :as set]))

(defn parse-input []
  (let [[p1 p2] (cs/split (u/input 16) #"\n\n\n\n")
        f       (fn [[a b c]]
                  {:before (read-string (second (cs/split a #": ")))
                   :op     (mapv read-string (cs/split b #" "))
                   :after  (read-string (second (cs/split c #": ")))})]
   {:samples (->> (cs/split-lines p1)
                  (filter (partial not= ""))
                  (partition 3)
                  (map f))
    :program (->> (cs/split-lines p2)
                  (filter (partial not= ""))
                  (map #(mapv read-string (cs/split % #" "))))}))

(def opcodes [:addr :addi :mulr :muli :banr :bani :borr :bori
              :setr :seti :gtir :gtri :gtrr :eqir :eqri :eqrr])

(defn apply-opcode [rs op [_ a b c]]
  (letfn [(gt [a b] (if (> a b) 1 0))
          (eq [a b] (if (= a b) 1 0))]
    (case op
      :addr (assoc rs c (+ (rs a) (rs b)))
      :addi (assoc rs c (+ (rs a) b))
      :mulr (assoc rs c (* (rs a) (rs b)))
      :muli (assoc rs c (* (rs a) b))
      :banr (assoc rs c (bit-and (rs a) (rs b)))
      :bani (assoc rs c (bit-and (rs a) b))
      :borr (assoc rs c (bit-or (rs a) (rs b)))
      :bori (assoc rs c (bit-or (rs a) b))
      :setr (assoc rs c (rs a))
      :seti (assoc rs c a)
      :gtir (assoc rs c (gt a (rs b)))
      :gtri (assoc rs c (gt (rs a) b))
      :gtrr (assoc rs c (gt (rs a) (rs b)))
      :eqir (assoc rs c (eq a (rs b)))
      :eqri (assoc rs c (eq (rs a) b))
      :eqrr (assoc rs c (eq (rs a) (rs b))))))

(defn attempt [{:keys [before op after]} opcode]
  (= (apply-opcode before opcode op) after))

(defn works-with [sample]
  (set (filter (partial attempt sample) opcodes)))

(defn part-1 []
  (->> (:samples (parse-input))
       (filter #(>= (count (works-with %)) 3))
       (count)))

(defn find-opcodes-numbers [samples]
  (let [can-be? (fn [samples]
                  [(-> samples first :op first)
                   (apply set/intersection (map works-with samples))])
        x (->> samples
               (sort-by (comp first :op))
               (partition-by (comp first :op))
               (map can-be?)
               (sort-by (comp count second)))
        decided? (fn [ops] (map (fn [[k v]] [k (first v)])
                                (filter #(= 1 (count (second %))) ops)))]
    (loop [ops x, decided {}]
      (if (empty? ops)
        decided
        (let [decitions (set (map first (decided? ops)))
              decided'  (reduce conj decided (decided? ops))
              used      (set (map second decided'))
              ops'      (map #(update % 1 (partial filter (complement used)))
                             (filter (comp not decitions first) ops))]
          (recur ops' decided'))))))
          
(defn part-2 []
  (let [samples   (:samples (parse-input))
        program   (:program (parse-input))
        num->op   (find-opcodes-numbers samples)
        iteration (fn [rs [o a b c]] (apply-opcode rs (num->op o) [o a b c]))]
    (first (reduce iteration [0 0 0 0] program))))            
