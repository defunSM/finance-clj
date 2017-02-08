(ns finance-clj.core
  (:use [incanter.core :only [view]]
        [incanter.charts :only [xy-plot add-lines scatter-plot]])
  (:require [clj-time.core :as tc]
            [clj-time.periodic :as tp]
            [clj-time.coerce :as tco]))

(take 25 (filter (fn [x] (>= x 12))
                 (repeatedly (fn [] (rand 35)))))

(defn generate-prices [lower-bound upper-bound]
  (filter (fn [x] (>= x lower-bound))
          (repeatedly (fn [] (rand upper-bound)))))

(def y (take 50 (generate-prices 10 35)))
(def x (into [] (range (count y))))
(view (xy-plot x y))

(def pricelist (generate-prices 12 35))
(take 25 (map (fn [x] {:price x}) pricelist))

(take 25 (map (fn [x y]
                [x y])
              (map (fn [x] {:time x}) (iterate inc 0))
              (map (fn [x] {:price x}) pricelist)))

(take 25 (->> (map (fn [x y]
                     [x y])
                   (map (fn [x] {:time x}) (iterate inc 0))
                   (map (fn [x] {:price x}) pricelist))
              (map (fn [x] (merge (first x) (second x))))))

(defn generate-timeseries [pricelist]
  (map (fn [x y]
         {:time x :price y})
       (iterate inc 0)
       pricelist))

(def set (take 25 (generate-timeseries pricelist)))

(view (xy-plot (map :time set)
               (map :price set)))

;; Chapter 2

(defn random-in-range [lower upper]
  (let [r (rand upper)]
    (if (>= r lower)
      r
      (+ (rand (- upper lower))
         lower))))

(defn stochastic-k [last-price low-price high-price]
  (let [hlrange (- high-price low-price)
        hlmidpoint (/ hlrange 2)
        numerator (if (> last-price hlmidpoint)
                    (- last-price hlmidpoint)
                    (- hlmidpoint low-price))]
    (/ numerator hlrange)))

(defn break-local-minima-maxima [k]
  (as-> k k
    (if (<= (int (+ 0.95 k)) 0)
      (+ 0.15 k) k)
    (if (>= k 1)
      (- k 0.15) k)))

(defn generate-prices
  ([low high]
   (generate-prices (random-in-range low high)))
  ([last-price]
   (iterate (fn [{:keys [last]}]
              (let [low (- last 5)
                    high (+ last 5)
                    k (stochastic-k last low high)
                    plus-OR-minus (rand-nth [- +])
                    kpm (if (= plus-OR-minus +)
                          (+ 1 (break-local-minima-maxima k))
                          (- 1 (break-local-minima-maxima k)))
                    newprice (* kpm last)
                    newlow (if (< newprice low) newprice low)
                    newhigh (if (> newprice high) newprice high)]
                {:last newprice}))
            {:last last-price})))

(defn generate-timeseries
  ([pricelist]
   (generate-timeseries pricelist (tc/now)))
  ([pricelist datetime]
   (->> (map (fn [x y] [x y])
             (map (fn [x] {:time x}) (iterate #(tc/plus % (tc/seconds (rand 4))) datetime))
             (map (fn [x] {:price x}) pricelist))
        (map (fn [x] (merge (first x) (second x)))))))

(def pricelist (generate-prices 5 15))

(def prices (map :last (map :price (take 40 (generate-timeseries pricelist)))))

(def num (count prices))

(defn moving-avg [n prices]
  (/ (reduce + (first (split-at (+ n 1) prices))) (+ n 1)))

(defn create-moving-avg [prices]
  (loop [n 0 acc []]
    (if (> n (count prices))
      acc
      (recur (inc n) (conj acc (moving-avg n prices))))))

(def mv-avg (create-moving-avg))

(defn create-graph [lower-bound upper-bound]
  (let [pricelist (generate-prices lower-bound upper-bound)
        prices (map :last (map :price (take 40 (generate-timeseries pricelist))))
        mv-avg (create-moving-avg prices)]
    (view (add-lines (xy-plot (range (count prices)) prices)
                     (range (count prices)) (create-moving-avg prices)))))

(create-graph 5 15)

(defn moving-average [tick-seq tick-window]
  (partition tick-window 1 tick-seq))

(def pricelist (generate-prices 5 15))
(def timeseries (generate-timeseries pricelist))
(def our-average (moving-average timeseries 20))

(take 2 our-average)
