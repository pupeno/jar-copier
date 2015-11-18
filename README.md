# jar-copier

[![Join the chat at https://gitter.im/carouselapps/jar-copier](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/carouselapps/jar-copier?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[![Build Status](https://travis-ci.org/carouselapps/jar-copier.svg?branch=master)](https://travis-ci.org/carouselapps/jar-copier)

[jar-copier](https://carouselapps.com/jar-copier/) is a Leiningen plugin to copy a jar from your dependencies into your
resources. This is necessary in the case of Java agents for example, which have to be present as jars outside the uberjar.

## Usage

In your `project.clj`, in the `:plugins` section, add:

[![Clojars Project](http://clojars.org/com.carouselapps/jar-copier/latest-version.svg)](http://clojars.org/com.carouselapps/jar-copier)

To run this plug in, execute:

    $ lein jar-copier

If you want the task to run automatically, which is recommended, add:

    :prep-tasks ["javac" "compile" "jar-copier"]

and it'll be invoked every time you build your uberjar.

The essential plug-in configuration goes into your `project.clj` and looks like this:

    :jar-copier {:destination "resources/jars"}

`:destination` specifies where to copy the jars. You can then specify the jars you want to copy in this fashion:

    :jar-copier {:jars        '[[org.clojure/clojure "1.7.0"]]
                 :destination "resources/jars"}

or, if you have `:java-agents` in your project, there's a shortcut to just copy them:

    :jar-copier {:java-agents true
                 :destination "resources/jars"}

They can both be mixed. For example, from
[proclodo-spa-server-rendering](https://github.com/ldnclj/proclodo-spa-server-rendering):

    (defproject proclodo-spa-server-rendering "0.1.0-SNAPSHOT"
      :dependencies [[org.clojure/clojure "1.7.0"]]
      :plugins [[jar-copier "0.1.0"]]
      :prep-tasks ["javac" "compile" "jar-copier"]
      :java-agents [[com.newrelic.agent.java/newrelic-agent "3.20.0"]]
      :jar-copier {:java-agents true
                   :destination "resources/jars"})

## Change log

### v0.3.0
- Added tests for misconfiguration.
- Added the possibility to manually specify the jars (not java-agents).

### v0.2.0 - 2015-09-04
- Changed the groupId to com.carouselapps
- Added a test
- Added travis ci
- Improved documentation

### v0.1.0 - 2015-09-03
- Initial release supporting copying java-agents.

## License

Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
