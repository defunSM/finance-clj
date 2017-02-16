(ns finance-clj.graphpage
  (:use hiccup.core
        hiccup.element
        hiccup.page))

;; This will be the page for graphing specific financial graphs to see relationships and etc.

(defn graph-page
  [title js body & {:keys [extra-js] :or {extra-js []}}]
  (html5
   [:head
    [:title title]
    (include-css "/css/nv.d3.css")
    (include-css "/css/results.css")
    (include-css "/css/font-awesome.css")
    (include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css")
    (include-css "https://fonts.googleapis.com/css?family=Crimson+Text")
    (include-css "https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css")
    [:body (concat
            [body]
            [(include-js "https://d3js.org/d3.v3.min.js")
             (include-js "/js/nv.d3.min.js")]
            (map include-js extra-js)
            [(include-js "/js/script.js")
             (javascript-tag js)])]
    ]))
