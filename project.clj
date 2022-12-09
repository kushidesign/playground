(defproject design.kushi/playground "0.1.0-SNAPSHOT"
  :description "Interactive documentation system for web UI."
  :url "github.com/kushidesign/playground"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[reagent/reagent                       "1.1.0"]
                 [applied-science/js-interop            "0.3.3"]
                 [org.clojars.paintparty/kushi          "1.0.0-a.15"]
                 [org.clojars.paintparty/par            "1.1.0"]
                 [binaryage/devtools                    "1.0.3"]
                 [metosin/malli                         "0.8.4"]
                 [markdown-to-hiccup/markdown-to-hiccup "0.6.2"]]
  :repl-options {:init-ns playground.core})
