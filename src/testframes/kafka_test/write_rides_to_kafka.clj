(ns testframes.kafka-test.write-rides-to-kafka
  (:use bridge.environment
        bridge.datastreams
        bridge.connectors.kafka)
  (:import (com.dataartisans.flinktraining.exercises.datastream_java.sources TaxiRideSource)
           (transformations ClojuredNYCFilter)
           (com.dataartisans.flinktraining.exercises.datastream_java.utils TaxiRideSchema)))

(def taxi-source "..\\..\\..\\resources\\datasets\\nycTaxiRides.gz")
(def max-event-delay 60)                                    ; events are out of order by max 60 seconds
(def serving-speed-factor 600)                              ; events of 10 minutes are served in 1 second
(def local-kafka-broker "localhost:9092")
(def cleansed-rides-topic "cleansed-rides")

(def exec-env (stream-execution-environment))
(use-event-time exec-env)

(def rides
  (add-source exec-env (TaxiRideSource. taxi-source max-event-delay serving-speed-factor)))

(def filtered-rides (.filter rides (ClojuredNYCFilter.)))

(add-sink filtered-rides (create-kafka-producer local-kafka-broker cleansed-rides-topic (TaxiRideSchema.)))

(execute exec-env)