;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns leiningen.jar-copier-test
  (:require [clojure.test :refer :all]
            [leiningen.jar-copier :refer :all]
            [clojure.java.io :as io]))

(deftest no-jar-copier-config-issues-warning
  (let [warnings (atom 0)]
    (with-redefs [leiningen.core.main/warn (fn [& _] (swap! warnings inc))]
      (jar-copier {}))
    (is (= 1 @warnings))))

(deftest no-destination-issues-warning
  (let [warnings (atom 0)]
    (with-redefs [leiningen.core.main/warn (fn [& _] (swap! warnings inc))]
      (jar-copier {:jar-copier {}}))
    (is (= 1 @warnings))))

(deftest nothing-to-copy-issues-warning
  (let [warnings (atom 0)]
    (with-redefs [leiningen.core.main/warn (fn [& _] (swap! warnings inc))]
      (jar-copier {:jar-copier {:destination "target/jars"}}))
    (is (= 1 @warnings))))

(deftest lack-of-java-agents-issues-warning
  (let [warnings (atom 0)]
    (with-redefs [leiningen.core.main/warn (fn [& _] (swap! warnings inc))]
      (jar-copier {:jar-copier {:java-agents true
                                 :destination "target/jars"}}))
    (is (= 1 @warnings))))

(deftest copy-java-agent
  (jar-copier {:java-agents '[[org.clojure/clojure "1.7.0"]]
               :jar-copier  {:java-agents true
                             :destination "target/jars"}})
  (is (.exists (io/as-file "target/jars/org.clojure/clojure.jar")))
  (io/delete-file "target/jars/org.clojure/clojure.jar"))

(deftest copy-plain-jar
  (jar-copier {:jar-copier {:jars        '[[org.clojure/clojure "1.7.0"]]
                            :destination "target/jars"}})
  (is (.exists (io/as-file "target/jars/org.clojure/clojure.jar")))
  (io/delete-file "target/jars/org.clojure/clojure.jar"))

(deftest copy-plain-jar-and-java-agents
  (jar-copier {:java-agents '[[org.clojure/clojure "1.7.0"]]
               :jar-copier  {:jars        '[[org.clojure/clojure-contrib "1.2.0"]]
                             :java-agents true
                             :destination "target/jars"}})
  (is (.exists (io/as-file "target/jars/org.clojure/clojure.jar")))
  (is (.exists (io/as-file "target/jars/org.clojure/clojure-contrib.jar")))
  (io/delete-file "target/jars/org.clojure/clojure.jar")
  (io/delete-file "target/jars/org.clojure/clojure-contrib.jar"))
