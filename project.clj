;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(defproject jar-copier "0.1.0"
  :description "Copy a jar from your dependencies into your resources."
  :url "https://github.com/carouselapps/jar-copier"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :lein-release {:deploy-via :clojars}
  :signing {:gpg-key "F2FB1C6F"}
  :scm {:name "git"
        :url  "https://github.com/carouselapps/jar-copier"}
  :eval-in-leiningen true)
