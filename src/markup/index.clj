(ns markup.index
  (:require
    [views.components.page :refer [page header footer]]
    [views.components.icons :as i]))

(defn blurb []
  [:section.blurb
   [:span
    "Deeper than the many different languages we speak, is the message we wish to communicate.
    Having an unfailing goal of understanding this message is how I provide value,
    not only to my associates, but also to my greater community."]])

(defn liked-tech []
  [:section.liked-tech

   [:div.heading "Development Experience"]

   [:div.content
    [:ul.wrapped
     [:li "Reagent (Figwheel + Leiningen)"]
     [:li "React"]
     [:li "Electron"]
     [:li "Flutter (Dart)"]
     [:li "Clojure"]
     [:li "Node.js"]
     [:li "AWS"]
     [:li "Google Cloud"]
     [:li "Firebase"]
     [:li "Vercel"]
     ]

    [:div.content
     [:div
      "This website was built using a custom built static site generator.
      "
      [:a {:href "build.html"} "Learn more about it here."]]]]])

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


(defn content []
  (page
    "gavan's work"
    [:div.container.index
     [:div
      (header)
      (blurb)
      (liked-tech)
      (links)
      (footer)
      ]

     ]))

(def export
  (with-meta
    content
    {:ssg
     {:path "index"}}))