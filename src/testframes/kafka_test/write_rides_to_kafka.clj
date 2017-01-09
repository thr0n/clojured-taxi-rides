(ns testframes.kafka-test.write-rides-to-kafka
  (:use bridge.environment bridge.datastreams bridge.connectors.kafka bridge.transformations)
  (:import (com.dataartisans.flinktraining.exercises.datastream_java.sources TaxiRideSource)
           (transformations ClojuredNYCFilter)
           (com.dataartisans.flinktraining.exercises.datastream_java.utils TaxiRideSchema))
  (:gen-class))

; linux
;(def taxi-source "/home/hendrik/dev/github/clojured-taxi-rides/resources/datasets/nycTaxiRides.gz")
; windows
(def taxi-source "C:\\dev\\github\\clojured-taxi-rides\\resources\\datasets\\nycTaxiRides.gz")

(def max-event-delay 60)                                    ; events are out of order by max 60 seconds
(def serving-speed-factor 600)                              ; events of 10 minutes are served in 1 second
(def local-kafka-broker "localhost:9092")
(def cleansed-rides-topic "clojure-rides2")

(defn -main [& args]
  (let [exec-env (stream-execution-environment)]
    (-> (use-event-time exec-env)                           ; threading macro
        (add-source (TaxiRideSource. taxi-source max-event-delay serving-speed-factor))
        (apply-filter (ClojuredNYCFilter.))
        (add-sink (create-kafka-producer local-kafka-broker cleansed-rides-topic (TaxiRideSchema.)))
        )
    (execute exec-env "ride-cleansing-to-kafka-v2")))
