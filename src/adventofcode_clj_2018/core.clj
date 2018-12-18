(ns adventofcode-clj-2018.core
  (:require [adventofcode-clj-2018.util :as u]
            [adventofcode-clj-2018.day01]
            [adventofcode-clj-2018.day02]
            [adventofcode-clj-2018.day03]
            [adventofcode-clj-2018.day04]
            [adventofcode-clj-2018.day05]
            [adventofcode-clj-2018.day06]
            [adventofcode-clj-2018.day07]
            [adventofcode-clj-2018.day08]
            [adventofcode-clj-2018.day09]
;; Not adding day10 because of the weird output.
;; Nevertheless tests do include it
            [adventofcode-clj-2018.day11]
            [adventofcode-clj-2018.day12]
            [adventofcode-clj-2018.day13]
            [adventofcode-clj-2018.day14]
            [adventofcode-clj-2018.day16]
;; Solution to day 17 is too slow for now
            [adventofcode-clj-2018.day18]))

(def completed [1 2 3 4 5 6 7 8 9   11 12 13 14   16   18])

(defn my-format [x]
  (str x (apply str (take (- 26 (count (str x))) (repeat " "))) " |"))

(defn day-results [n]
  (let [fn (if (> n 9)
             (str "(adventofcode-clj-2018.day" n)
             (str "(adventofcode-clj-2018.day0" n))
        sn (if (> n 9) (str n) (str " " n))
        p1 (->> (str fn "/part-1)") read-string eval)
        p2 (->> (str fn "/part-2)") read-string eval)]
    (println (str " | " sn "  |  *   | " (my-format p1)))
    (println (str " |     |  **  | " (my-format p2)))))

(defn -main [& args]
  (let [args' (if (empty? args) completed (map u/parse-int args))]
    (time
      (do (println "\n  =========================================")
          (println " | Day | Star | Solution                   |")
          (println " |=========================================|")
          (doseq [day args'] (day-results day))
          (println "  =========================================")))))
