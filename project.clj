(defproject thread-ring "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.6.0-beta2"]
                 [criterium "0.4.3"]]
  :profiles {:core.async {:dependencies [[org.clojure/core.async "0.1.278.0-76b25b-alpha"]]}
             :pulsar {:dependencies [[co.paralleluniverse/pulsar "0.4.0"]]
                      :java-agents [[co.paralleluniverse/quasar-core "0.4.0"]]}}
  :jvm-opts ^:replace ["-Xms1G" "-Xmx2G"]
  :main thread-ring.core
  :aliases {"core.async" ["with-profile" "+core.async" "run"]
            "pulsar" ["with-profile" "+pulsar" "run"]})
