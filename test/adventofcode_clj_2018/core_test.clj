(ns adventofcode-clj-2018.core-test
  (:require [clojure.test :refer :all]
            [adventofcode-clj-2018.day01 :as day01]
            [adventofcode-clj-2018.day02 :as day02]
            [adventofcode-clj-2018.day03 :as day03]
            [adventofcode-clj-2018.day04 :as day04]
            [adventofcode-clj-2018.day05 :as day05]
            [adventofcode-clj-2018.day06 :as day06]
            [adventofcode-clj-2018.day07 :as day07]
            [adventofcode-clj-2018.day08 :as day08]
            [adventofcode-clj-2018.day09 :as day09]
            [adventofcode-clj-2018.day10 :as day10]
            [adventofcode-clj-2018.day11 :as day11]
            [adventofcode-clj-2018.day12 :as day12]
            [adventofcode-clj-2018.day13 :as day13]
            [adventofcode-clj-2018.day14 :as day14]
            [adventofcode-clj-2018.day16 :as day16]
; solution to day 17 is too slow for now
            [adventofcode-clj-2018.day18 :as day18]
            [adventofcode-clj-2018.day19 :as day19]))

(deftest day01-part-1 (is (= (day01/part-1) 484)))
(deftest day01-part-2 (is (= (day01/part-2) 367)))

(deftest day02-part-1 (is (= (day02/part-1) 5681)))
(deftest day02-part-2 (is (= (day02/part-2) "uqyoeizfvmbistpkgnocjtwld")))

(deftest day03-part-1 (is (= (day03/part-1) 109716)))
(deftest day03-part-2 (is (= (day03/part-2) 124)))

(deftest day04-part-1 (is (= (day04/part-1) 103720)))
(deftest day04-part-2 (is (= (day04/part-2) 110913)))

(deftest day05-part-1 (is (= (day05/part-1) 9288)))
(deftest day05-part-2 (is (= (day05/part-2) 5844)))

(deftest day06-part-1 (is (= (day06/part-1) 3882)))
(deftest day06-part-2 (is (= (day06/part-2) 43852)))

(deftest day07-part-1 (is (= (day07/part-1) "BGJCNLQUYIFMOEZTADKSPVXRHW")))
(deftest day07-part-2 (is (= (day07/part-2) 1017)))

(deftest day08-part-1 (is (= (day08/part-1) 44338)))
(deftest day08-part-2 (is (= (day08/part-2) 37560)))

(deftest day09-part-1 (is (= (day09/part-1) 388024)))
(deftest day09-part-2 (is (= (day09/part-2) 3180929875)))

(deftest day10-part-1 (is (= (day10/part-1) ["#       #####    ####   #####   #####   #    #  ######  ######"
                                             "#       #    #  #    #  #    #  #    #  #    #  #            #"
                                             "#       #    #  #       #    #  #    #  #    #  #            #"
                                             "#       #    #  #       #    #  #    #  #    #  #           # "
                                             "#       #####   #       #####   #####   ######  #####      #  "
                                             "#       #  #    #  ###  #       #    #  #    #  #         #   "
                                             "#       #   #   #    #  #       #    #  #    #  #        #    "
                                             "#       #   #   #    #  #       #    #  #    #  #       #     "
                                             "#       #    #  #   ##  #       #    #  #    #  #       #     "
                                             "######  #    #   ### #  #       #####   #    #  ######  ######"])))
(deftest day10-part-2 (is (= (day10/part-2) 10011)))

(deftest day11-part-1 (is (= (day11/part-1) "243,16")))
(deftest day11-part-2 (is (= (day11/part-2) "231,227,14")))

(deftest day12-part-1 (is (= (day12/part-1) 3890)))
(deftest day12-part-2 (is (= (day12/part-2) 4800000001087)))

(deftest day13-part-1 (is (= (day13/part-1) "5,102")))
(deftest day13-part-2 (is (= (day13/part-2) "46,45")))

(deftest day14-part-1 (is (= (day14/part-1) 1115317115)))
(deftest day14-part-2 (is (= (day14/part-2) 20229822)))

(deftest day16-part-1 (is (= (day16/part-1) 640)))
(deftest day16-part-2 (is (= (day16/part-2) 472)))

;; (deftest day17-part-1 (is (= (day17/part-1) 42389)))
;; (deftest day17-part-2 (is (= (day17/part-2) 34497)))

(deftest day18-part-1 (is (= (day18/part-1) 519552)))
(deftest day18-part-2 (is (= (day18/part-2) 165376)))

(deftest day19-part-1 (is (= (day19/part-1) 1350)))
(deftest day19-part-2 (is (= (day19/part-2) 15844608)))
