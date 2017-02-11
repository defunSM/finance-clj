(defproject finance-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [incanter "1.5.5"]
                 [clj-time "0.9.0"]
                 [ring/ring-core "1.5.1"]
                 [ring/ring-jetty-adapter "1.5.1"]
                 [compojure "1.5.2"]
                 [hiccup "1.0.5"]]
  :plugins [[lein-ring "0.11.0"]]
  :ring {:handler finance-clj.analytics/app})
