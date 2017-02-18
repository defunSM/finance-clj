(ns financialclj.barchart
  (:require [financial-clj.core :as f]))

(defn count-point [pair]
  (let [[diet items] pair]
    (f/Point. diet (count items) 1)))

(defn get-diet-counts [diet-groups]
  (apply array (map count-point diet-groups)))

(defn sum-by [key-fn coll]
  (reduce + 0 (map key-fn coll)))

(defn weight-point [pair]
  (let [[diet items] pair
        weight-total (sum-by #(.-weight %) items)]
    (f/Point. diet weight-total 1)))

(defn get-diet-weights [diet-groups]
  (apply array (map weight-point diet-groups)))

(defn json->nv-groups [json]
  (let [diet-groups (group-by #(.-diet %) json)]
    (array (f/Group.
            "Chick Counts"
            (get-diet-counts diet-groups))
           (f/Group.
            "Chick Weights"
            (get-diet-weights diet-groups)))))

(defn ^:export bar-chart []
  (f/create-chart "/barchart/data.json"
                  "#barchart svg"
                  #(.multiBarChart js/nv.models)
                  json->nv-groups
                  :x-label "Chicks"))
