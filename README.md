# jar-copier

A Leiningen plugin to copy a jar from your dependencies into your resources. This is necesarry in the case of Java
agents for example, which have to be present as jars outside the uberjar.

## Usage

Put `[jar-copier "0.1.0"]` into the `:plugins` vector on your `project.clj`. To run this plug in, execute:

    $ lein jar-copier

If you want the task to run automatically, which is recommended, add:

    :prep-tasks ["javac" "compile" "jar-copier"]

and it'll be invoked every time you build your uberjar.

You need to configure the plug-in in your `project.clj` like this:

    :jar-copier {:java-agents true
                 :destination "resources/jars"}

`:java-agents` instruct this plug in to automatically copy any jars that are specified as Java agents. `:destination`
specifies where to copy them to.

## TODO

This plug in only copies jars specified as java agents but there's potential for more. See:
https://github.com/carouselapps/jar-copier/issues/1

## License

Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
