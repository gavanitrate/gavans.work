(ns markup.build
  (:require [views.components.page :refer [page header footer]]))

(defn description []
  [:section

   [:div.heading "How I Built This Website"]

   [:div.content
    [:div
     "I have a deep love for Clojure and functional programming.
      Furthermore, my experience with
      "
     [:a {:href "https://github.com/reagent-project/reagent"} "Reagent"]
     "
     has shown me the benefits of defining application views in pure data.
     So, in the process of developing this very lightweight website,
     I began my search for a simple static site generator that supported writing markup in
     "
     [:a {:href "https://github.com/weavejester/hiccup"} "Hiccup"]
     ". "
     "Unfortunately, I quickly learned there was no existing solution.
     But there hasn't yet been a goal I haven't been able to achieve.
     "]

    [:div
     "
     Born from this frustration is a simple static site generator
     that supports processing markup in Hiccup, styling in SCSS
     and a watcher service to rebuild when changes to the source
     code are detected - all written in pure Clojure.
     "
     [:a {:href "test.com"} "You can find the source code here."]
     ]
    ]])

(defn content []
  (page
    "Building gavans.work"
    [:div.container.index
     [:div
      (header)
      (description)
      (footer)
      ]

     ]))

(def export
  (with-meta
    content
    {:ssg
     {:path "build"}}))