(defproject clojured-taxi-rides "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [joda-time/joda-time "2.9.6"]
                 [com.data-artisans/flink-training-exercises "0.6"]]
  :aot [transformations.Clojured-NYCFilter
        transformations.Clojured-GridMatcher
        transformations.Clojured-KeySelector
        transformations.Clojured-RideCounter
        transformations.Clojured-ThresholdFilter
        transformations.Clojured-Grid2Coordinates]
  :java-source-paths ["src/java_interfaces"]
  :main testframes.popular-places)
