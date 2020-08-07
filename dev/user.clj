(ns user
  (:require
    [clojure.java.shell :refer [sh]]
    [ssg.main]))


(comment

  (def ssg
    (ssg.main/start!
      {:profile        :prod
       :sass-options   {:source-paths ["src/styles/"]
                        :target-path  "public/"}
       :markup-options {:source-paths ["src/markup/"]
                        :target-path  "public/"}
       }))

  ssg

  (ssg.main/rebuild
    ssg
    {:profile        :prod
     :sass-options   {:source-paths ["src/styles/"]
                      :target-path  "public/"}
     :markup-options {:source-paths ["src/markup/"]
                      :target-path  "public/"}
     })

  (ssg.main/stop! ssg)

  (sh "wt")

  )