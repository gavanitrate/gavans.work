(ns views.components.contact
  (:require [views.components.icons :as i]))

(defn links []
  [:section.solid.links
   [:div.content
    [:div.row
     [:a.invisible
      {:href   "https://github.com/gavanitrate"
       :target "_blank"}
      [:div.icon (i/svg-icon i/github)]]

     [:a.invisible
      {:href   "https://www.linkedin.com/in/gavansingh/"
       :target "_blank"}
      [:div.icon (i/svg-icon i/linkedin)]]

     [:a.invisible
      {:href   "mailto:gav@gavans.work"
       :target "_blank"}
      [:div.icon (i/svg-icon i/email)]]
     ]]])