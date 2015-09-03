;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns leiningen.jar-copier
  (:require [leiningen.core.main :as lein]
            [cemerick.pomegranate.aether :as aether]
            [clojure.java.io :as io]))

(defn- copy-java-agents [java-agents destination]
  (doseq [[jar-file full-destination] (map list
                                           (aether/dependency-files (aether/resolve-dependencies :coordinates java-agents))
                                           (map #(io/file destination (str (first %) ".jar")) java-agents))]
    (lein/info "Coping" (.getPath jar-file) "to" (.getPath full-destination))
    (io/make-parents full-destination)
    (io/copy jar-file full-destination)))

(def misses? (complement contains?))

(defn jar-copier "Copy a jar from your dependencies into your resources."
  [project & args]
  (if (misses? project :jar-copier)
    (lein/warn "jar-copier is not configured.")
    (do
      (if (misses? (:jar-copier project) :destination)
        (lein/warn "jar-copier is missing the destination configuration")
        (if (and (get-in project [:jar-copier :java-agents])
                 (misses? project :java-agents))
          (lein/warn "jar-copier requested to copy java agents, but no java agents were present.")
          (copy-java-agents (:java-agents project) (get-in project [:jar-copier :destination])))))))