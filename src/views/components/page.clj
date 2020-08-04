(ns views.components.page
  (:require [hiccup.page :refer [html5 include-css]]))

(defn page [title content]
  (html5
    {:lang "en"}
    [:head
     [:meta {:charset "UTF-8"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1, maximum-scale=1"}]
     [:link {:href "assets/code-braces-box.svg" :rel "icon"}]
     [:title title]
     (include-css "styles.css")]
    [:body content]))

(defn header []
  [:section.crown
   [:a.invisible
    {:href "/"}
    [:div.typo-headline "gAvAn singh"]]
   [:div.typo-caption "Software Engineer"]])

(defn footer []
  [:section [:div.row [:span "© Gavan Singh, 2020"]]])