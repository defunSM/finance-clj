(ns finance-clj.web
  (:use [incanter.core :only [view]]
        [incanter.charts :only [xy-plot add-lines scatter-plot]]
        [incanter.io :only [read-dataset]]
        compojure.core
        ring.adapter.jetty
        [ring.middleware.content-type :only (wrap-content-type)]
        [ring.middleware.file :only (wrap-file file-request)]
        [ring.middleware.file-info :only (wrap-file-info)]
;;        [ring.middleware.stacktrace :only (wrap-stacktrace)]
        [ring.util.response :only (redirect file-response)]
        hiccup.core
        hiccup.element
        hiccup.page
        [hiccup.middleware :only (wrap-base-url)]
        [finance-clj.trynow :only [trynow-page]]
        [finance-clj.graphpage :only [graph-page]])
  (:require [clj-time.core :as tc]
            [clj-time.periodic :as tp]
            [clj-time.coerce :as tco]
            [compojure.route :as route]
            [compojure.handler :as handler]))

(def data (read-dataset "data.csv" :header true))

(def collection (map (fn [x] (if (> x 200)
                              x)) (map :Open (vec (:rows data)))))

(defn query-helper

  "This is a function to help query-data sort through csv files. It takes the data as the first value. Than the header which is a keyword.
   Than it will take a value you are testing for and than the sign that you are testing. So if you want to find all numbers greater than 250
   you would use a < sign as the sign argument."

  [data header sign value]
  (filter identity (for [i (:rows data)]
                     (if (sign (header i) value)
                       i
                       nil))))

(query-helper data :Open < 200)

(defn query-data

  "This is a function used to help sort through csv files. If one argument is provided the data must be using
   (read-dataset 'nameofdata.csv' :header true). This will make query-data return the headers of the csv.
   Otherwise using two arguments you can provide the header and show purely that header from the csv. You must
   use the key that is linked to that header so an example is that if there is a :Open key (query-data :Open data)
   will result in the list showing everything in that column."

  ([data]
   (let [raw-data (vec (:rows data))
         headers (keys (first raw-data))]
     headers))
  ([arg data]
   (let [raw-data (vec (:rows data))
         headers (keys (first raw-data))
         elements (count raw-data)
         specifics (map arg raw-data)]
     specifics)))

(query-data data)

;; finish this function to query the data based on what you want to find.

(defn query-data [arg]
  )

(get (vec (:rows data)) 1)

(keys (first (:rows data)))



(defn scatter-charts []
  (graph-page "Scatter Chart"
              "financialclj.scatter.scatter_plot();"
              [:div#scatter.chart [:svg]]))

(defn bar-chart []
  (graph-page "Bar Chart"
              "financialclj.barchart.bar_chart();"
              [:div#barchart.chart [:svg]]))

(defn hist-plot []
  (graph-page "Histogram"
              "financialclj.histogram.histogram();"
              [:div#histogram.chart [:svg]]))

(defn time-series []
  (graph-page "IBM Stock Data"
              "financialclj.time.ibm_stock();"
              [:div#time.chart [:svg]]))

(defroutes
  site-routes
  (GET "/" [] (redirect "resources/index.html"))

  (GET "/scatter" [] (scatter-charts))
  (GET "/scatter/data.json" []
       (redirect "/data/census-race.json"))

  (GET "/barchart" [] (bar-chart))
  (GET "/barchart/data.json" []
       (redirect "/data/chick-weight.json"))

  (GET "/histogram" [] (hist-plot))
  (GET "/histogram/data.json" []
       (redirect "/data/abalone.json"))

  (GET "/ibm-stock" [] (time-series))
  (GET "/ibm-stock/data.csv" [] (redirect "/data/ibm.csv"))

  (GET "/trynow" [] (trynow-page))
;;  (GET "/graph" [] (graph-page "S" "S" "s"))
  (route/resources "/")
  (route/not-found "Page Not Found"))

(def app
  (-> (handler/site site-routes)
      (wrap-file "resources")
      (wrap-file-info)
      (wrap-content-type)))

(def raw-data (clojure.string/split (slurp "data.csv") #"\n")) ;; Raw data of the csv file. Splitting by new line.
(def labels (clojure.string/split (get raw-data 0) #",")) ;; Extracts the labels from the csv file.
(def data-pts (- (count raw-data) 1)) ;; Contains the number of vectors in the raw-data from the csv-file.


;; Formats the raw-data into date, open, high, low, close, volume and adj-close in a hash map.
;; starting from the most recent date so it will need to be reversed if you want it from lowest date to most recent date.
;; The values for the keys are strings so it will need to be converted to a integer or float if you want to use view.
;; This can be written more elegantly but right now it works.

(def data (for [i (range 1 data-pts)]
            (let [csv (get raw-data i)
                  date (get (clojure.string/split csv #",") 0)
                  open (get (clojure.string/split csv #",") 1)
                  high (get (clojure.string/split csv #",") 2)
                  low (get (clojure.string/split csv #",") 3)
                  close (get (clojure.string/split csv #",") 4)
                  volume (get (clojure.string/split csv #",") 5)
                  adj-close (get (clojure.string/split csv #",") 6)]
              {:date date :open open :high high :low low :close close :volume volume :adj-close adj-close})))

;; Function to help data be turned into p-data (Processed Data) so that the values are floats rather than strings.
;; This happens by using read-string to convert all the values to the given string to a number.
;; The argument val is the key that you want to convert all the values of such as "open" from data.

(defn map->float [val]
  (map read-string (map val data)))

(def p-data
  (let [date (map :date data)
        open (map->float :open)
        high (map->float :high)
        low (map->float :low)
        close (map->float :close)
        volume (map->float :volume)
        adj-close (map->float :adj-close)]
    {:date date :open open :high high :low low :close close :volume volume :adj-close adj-close}))


(view (add-lines (xy-plot (range data-pts) (reverse (:open p-data))
                          :legend true
                          :x-label "Date"
                          :y-label "Price ($)"
                          :title "Financial Graph")
                 (range data-pts) (reverse (:close p-data))))

(add-lines (xy-plot (range data-pts) (reverse (:high p-data)))
           (range data-pts) (reverse (:low p-data)))
