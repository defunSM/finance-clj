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
                 [hiccup "1.0.5"]
                 [org.clojure/clojurescript "1.9.473"]
                 [environ "1.0.0"]
                 [compojure "1.4.0"]]
  :main finance-clj.web
  :min-lein-version "2.0.0"
  :plugins [[lein-ring "0.11.0"]
            [lein-cljsbuild "1.1.5"]
            [environ/environ.lein "0.3.1"]]
  :hooks [environ.leiningen.hooks]
  :ring {:handler finance-clj.web/app}

  :cljsbuild {:builds
              [{:source-paths ["src-cljs"],
                :compiler
                {:pretty-print true,
                 :output-to "resources/js/script.js",
                 :optimizations :whitespace}}]}

  :uberjar-name "clojure-getting-started-standalone.jar"

  :profiles {:production {:env {:production true}}}))
