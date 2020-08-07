(ns markup.not-found
  (:require [views.components.page :refer [page header footer]]
            [views.components.contact :refer [links]]))

(defn description []
  [:section

   [:div.heading "You seem lost."]

   [:div.content
    "Perhaps you would like to check out my GitHub for some fun projects.
    If you'd like to share something cool you're working on, shoot me an email.
    "
    ]])

(defn content []
  (page
    "Building gavans.work"
    [:div.container.index
     [:div
      (header)
      (description)
      (links)
      (footer)
      ]

     ]))

(def export
  (with-meta
    content
    {:ssg
     {:path "404"}}))