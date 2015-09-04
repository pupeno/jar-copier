;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns leiningen.jar-copier-test
  (:require [clojure.test :refer :all]
            [leiningen.jar-copier :refer :all]
            [clojure.java.io :as io]))

(deftest jar-copier-test
  (jar-copier {:java-agents '[[org.clojure/clojure "1.7.0"]]
               :jar-copier {:java-agents true
                            :destination "target/jars"}})
  (is (.exists (io/as-file "target/jars/org.clojure/clojure.jar"))))
