(ns financial-clj.core)

(defn ^:export hello [world]
  (js/alert (str "Hello, " world)))
