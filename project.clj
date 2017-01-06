(defproject clojured-taxi-rides "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/thr0n/clojured-taxi-rides"
  :license {:name "Apache License, Version 2.0"
            :url  "https://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [joda-time/joda-time "2.9.6"]
                 [com.data-artisans/flink-training-exercises "0.6"]
                 [clojure2flink "0.1.0-SNAPSHOT"]]
  :aot [transformations.Clojured-NYCFilter
        transformations.Clojured-GridMatcher
        transformations.Clojured-KeySelector
        transformations.Clojured-RideCounter
        transformations.Clojured-ThresholdFilter
        transformations.Clojured-Grid2Coordinates
        testframes.ride-cleansing
        testframes.popular-places
        testframes.kafka-test.write-rides-to-kafka
        testframes.kafka-test.read-rides-from-kafka
        ]
  :java-source-paths ["src/java_interfaces" "src/taxi_stuff" "src/references"]
  :resources-paths ["lib/"]
  ;:profiles {:uberjar {:aot :all}}
  :main testframes.ride-cleansing
  )
