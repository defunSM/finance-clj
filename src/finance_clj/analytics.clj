(ns finance-clj.analytics
  (:use [incanter.core :only [view]]
        [incanter.charts :only [xy-plot add-lines scatter-plot]])
  (:require [clj-time.core :as tc]
            [clj-time.periodic :as tp]
            [clj-time.coerce :as tco]))

(def raw-data (clojure.string/split (slurp "data.csv") #"\n")) ;; Raw data of the csv file. Splitting by new line.
(def labels (clojure.string/split (get raw-data 0) #",")) ;; Extracts the labels from the csv file.
(def data-points (- (count raw-data) 1)) ;; Contains the number of vectors in the raw-data from the csv-file.


;; Formats the raw-data into date, open, high, low, close, volume and adj-close in a hash map.
;; starting from the most recent date so it will need to be reversed if you want it from lowest date to most recent date.
;; The values for the keys are strings so it will need to be converted to a integer or float if you want to use view.

(def data (for [i (range 1 data-points)]
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


(view (xy-plot (range data-points) (reverse (:open p-data))))
