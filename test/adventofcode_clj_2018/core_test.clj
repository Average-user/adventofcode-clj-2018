(ns adventofcode-clj-2018.core-test
  (:require [clojure.test :refer :all]
            [adventofcode-clj-2018.day01 :as day01]
            [adventofcode-clj-2018.day02 :as day02]
            [adventofcode-clj-2018.day03 :as day03]
            [adventofcode-clj-2018.day04 :as day04]
            [adventofcode-clj-2018.day05 :as day05]
            [adventofcode-clj-2018.day06 :as day06]
            [adventofcode-clj-2018.day07 :as day07]
            [adventofcode-clj-2018.day08 :as day08]))

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
