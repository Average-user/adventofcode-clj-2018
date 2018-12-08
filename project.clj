(defproject adventofcode-clj-2018 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.match "0.3.0-alpha5"]]
  :main ^:skip-aot adventofcode-clj-2018.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
