(ns ssg.hiccup
  (:require [hiccup.core :as h]
            [clojure.string :as string])
  (:import (java.io File)))

(defn read-hiccup-file [file]
  (let [content (some-> file .getPath load-file var-get)
        {:keys [path] :as export} (some-> content meta :ssg)]
    (when (and (some? content) (map? export))
      {:content content
       :meta    export})))

(defn output-hiccup! [context {:keys [content meta]}]
  (let [{:keys [path]} meta
        {:keys [target-path]}
        (-> context :configuration :hiccup)]
    (spit (str target-path path ".html") (h/html (content)))))

(defn build-watcher [context]
  {:paths   (-> context :configuration :hiccup :source-paths)
   :filter  (fn [_ {:keys [kind file]}]
              (and
                (-> file .isFile)
                (= kind :modify)
                (-> file .getName (string/ends-with? ".clj"))))
   :handler (fn [ctx {:keys [kind file]}]
              (println file)
              (when-let [{:keys [meta] :as data} (read-hiccup-file file)]
                (let [{:keys [path]} meta]
                  (println (str "Compiling {html}... " path))
                  (output-hiccup! context data)))
              ctx)})

(defn build-paths! [context]

  )


(comment

  (read-hiccup-file (File. "src/markup/index.clj"))


  (let [source-paths ["src/markup/"]]
    (->> source-paths
         (map #(file-seq (File. %)))
         (apply concat)
         (filter (fn [f] (and (.isFile f) (-> file .getName (string/ends-with? ".clj")))))

         )

    #_(doseq [file]
        (->> file .isFile println)
        )
    )

  )