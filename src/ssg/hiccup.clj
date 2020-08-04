(ns ssg.hiccup
  (:require [hiccup.core :as h]
            [clojure.string :as string]
            [hawk.core :as hawk])
  (:import (java.io File)))

(defn read-hiccup-file [file]
  (let [content (some-> file .getPath load-file var-get)
        {:keys [path] :as export} (some-> content meta :ssg)]
    (when (and (some? content) (map? export))
      {:content content
       :meta    export})))

(defn output-hiccup! [target-path {:keys [content meta]}]
  (let [{:keys [path]} meta]
    (println (str "Compiling {html}... " path))
    (spit (str target-path path ".html") (h/html (content)))))

(defn build-watcher [{:keys [source-paths target-path] :as options}]
  {:paths   source-paths
   :filter  (fn [_ {:keys [kind file]}]
              (and
                (-> file .isFile)
                (= kind :modify)
                (-> file .getName (string/ends-with? ".clj"))))
   :handler (fn [ctx {:keys [kind file]}]
              (when-let [data (read-hiccup-file file)]
                (output-hiccup! target-path data))
              ctx)})

(defn build-hiccup! [{:keys [source-paths target-path] :as options}]
  (let [{:keys [source-paths]} options
        files (->> source-paths
                   (map #(file-seq (File. %)))
                   (apply concat)
                   (filter (fn [file] (and (.isFile file) (-> file .getName (string/ends-with? ".clj"))))))]
    (doseq [file files]
      (when-let [data (read-hiccup-file file)]
        (output-hiccup! target-path data)))))

(defn build [{:keys [auto] :as options}]
  (if auto
    (hawk/watch! [(build-watcher options)])
    (build-hiccup! options)))

(defn stop [watcher]
  (hawk/stop! watcher))


(comment

  (read-hiccup-file (File. "src/markup/index.clj"))

  (build-hiccup! (assoc-in {} [:configuration :hiccup :source-paths] ["src/markup/"]))

  (let [source-paths ["src/markup/"]]
    (when-let [{:keys [meta] :as data} (read-hiccup-file file)]
      (let [{:keys [path]} meta]
        (println (str "Compiling {html}... " path))
        (output-hiccup! context data)))

    )

  )