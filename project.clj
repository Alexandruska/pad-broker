(defproject pad-broker "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [aleph "0.4.2-alpha8"]]
  :main ^:skip-aot pad-broker.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
