;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns leiningen.jar-copier
  (:require [leiningen.core.main :as lein]
            [cemerick.pomegranate.aether :as aether]
            [clojure.java.io :as io]))

(defn- copy-jars [jars destination]
  (doseq [[jar-file full-destination] (map list
                                           (aether/dependency-files (aether/resolve-dependencies :coordinates jars))
                                           (map #(io/file destination (str (first %) ".jar")) jars))]
    (lein/info "Coping" (.getPath jar-file) "to" (.getPath full-destination))
    (io/make-parents full-destination)
    (io/copy jar-file full-destination)))

(def ^{:private true} misses? (complement contains?))

(defn jar-copier "Copy a jar from your dependencies into your resources."
  [project & args]
  (if (misses? project :jar-copier)
    (lein/warn "jar-copier is not configured. Your Leiningen project needs a :jar-copier entry.")
    (if (misses? (:jar-copier project) :destination)
      (lein/warn "jar-copier is missing the destination configuration. You :jar-copier entry needs a :destination value.")
      (if (and (misses? (:jar-copier project) :jars)
               (misses? (:jar-copier project) :java-agents))
        (lein/warn "jar-copier has nothing to copy, you need to specify :jars or :java-agents (or both).")
        (let [destination (get-in project [:jar-copier :destination])]
          (if (and (get-in project [:jar-copier :java-agents])
                   (misses? project :java-agents))
            (lein/warn "jar-copier requested to copy java agents, but no java agents were specified in your project.")
            (copy-jars (:java-agents project) destination))
          (when (contains? (:jar-copier project) :jars)
            (copy-jars (get-in project [:jar-copier :jars]) destination)))))))
