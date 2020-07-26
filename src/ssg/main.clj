(ns ssg.main
  (:require
    [clojure.spec.alpha :as s]
    [hawk.core :as hawk]
    [sass4clj.api :as sass]
    [ssg.hiccup]))

(defn build-dev-config [{:keys [sass-options
                                markup-options]
                         :as   options}]
  {:sass   (-> sass-options
               (assoc :source-map true
                      :output-style :expanded
                      :auto true))
   :hiccup markup-options})

(defn build-prod-config [{:keys [sass-options
                                 markup-options]
                          :as   options}]
  {:sass   (-> sass-options
               (assoc :source-map false
                      :output-style :compressed
                      :auto false))
   :hiccup markup-options})

(defn build-context [{:keys [profile]
                      :as   options}]
  (-> {}
      (assoc
        ::options options
        :configuration
        (case profile
          :dev (build-dev-config options)
          :prod (build-prod-config options)))))

(defn rebuild-context [context options]
  (merge context (build-context (or options (-> context ::options)))))

(defn start-sass-service [context]
  (-> context
      (assoc ::sass (-> context :configuration :sass
                        sass/build))))

(defn start-hiccup-service [context]
  (let [watcher (hawk/watch! [(ssg.hiccup/build-watcher context)])]
    (-> context (assoc ::hiccup watcher))))


;; specs for config arguments
(s/def ::profile #{:dev :prod})
(s/def ::options (s/keys :req-un [::profile]))

;; starts the build service
(defn start [{:keys [profile
                     sass-options
                     markup-options]
              :as   options}]
  (if-not (s/valid? ::options options)
    (s/explain-out (s/explain-data ::options options))
    (-> options
        build-context
        start-sass-service
        start-hiccup-service)))

(defn stop [context]
  (some-> context ::sass sass/stop)
  (some-> context ::hiccup hawk/stop!)
  (-> context
      (dissoc ::sass ::hiccup)))

(defn rebuild
  ([context] (rebuild context nil))
  ([context options]
   (-> context
       stop
       (rebuild-context options)
       start-sass-service
       start-hiccup-service)))