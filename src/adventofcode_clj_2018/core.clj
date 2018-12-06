(ns adventofcode-clj-2018.core
  (:require [adventofcode-clj-2018.util :as u]
            [adventofcode-clj-2018.day01]
            [adventofcode-clj-2018.day02]
            [adventofcode-clj-2018.day03]
            [adventofcode-clj-2018.day04]
            [adventofcode-clj-2018.day05]))

(def completed (range 1 6))

(defn my-format [x]
  (str x (apply str (take (- 25 (count (str x))) (repeat " "))) " |"))

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
      (do (println "\n  ========================================")
          (println " | Day | Star | Solution                  |")
          (println " |========================================|")
          (doseq [day args'] (day-results day))
          (println "  ========================================")))))
