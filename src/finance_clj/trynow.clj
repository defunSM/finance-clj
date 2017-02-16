(ns finance-clj.trynow
  (:use hiccup.core
        hiccup.element
        hiccup.page))

;; This is the try now page for managing personal finances.

(defn trynow-page []
  (html5 [:head
          (include-css "/css/results.css")
          (include-css "/css/font-awesome.css")
          (include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css")
          (include-css "https://fonts.googleapis.com/css?family=Crimson+Text")
          (include-css "https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css")
          [:title "Finances | Financeleaf"]
          [:body
           [:div {:class "dad"}
            [:nav {:class "navbar navbar-default"}
             [:div {:class "container"}
              [:div {:class "navbar-header"}
               [:button {:type "button" :class "navbar-toggle collapsed"
                         :data-toggle "collapse"
                         :data-target "#bs-example-navbar-collapse-1"
                         :aria-expanded "false"}
                [:span {:class "sr-only"} "Toggle navigation"]
                [:span {:class "icon-bar"}]
                [:span {:class "icon-bar"}]
                [:span {:class "icon-bar"}]]
               [:a {:class "navbar-brand" :href "/"} "Financeleaf"]]
              [:div {:class "collapse navbar-collapse" :id "bs-example-navbar-collapse-1"}
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li [:a {:href "/"} "Home"]]
                [:li {:class "active"} [:a {:href "/"} "Try Financeleaf"]]
                [:li [:a {:href "/"} "Login"]]
                [:li [:a {:href "/"} "Sign up"]]]]
              ]]
            [:div {:class "well well-lg result"}
             [:p "Balance" [:span {:id "words"} [:i {:class "fa fa-usd" :aria-hidden "true"}] [:input {:class "buttontrynow"}]]

              [:span {:id "green"} [:i {:class "fa fa-caret-up" :aria-hidden "true"}]]
              [:span {:id "red"} [:i {:class "fa fa-caret-down" :aria-hidden "true"}]]
              [:a [:i {:class "fa fa-info-circle" :aria-hidden "true"}]]

              ]]]
           ]]))
