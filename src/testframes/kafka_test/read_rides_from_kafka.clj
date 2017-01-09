(ns testframes.kafka-test.read-rides-from-kafka
  (:use bridge.environment bridge.datastreams bridge.connectors.kafka bridge.transformations bridge.windowing)
  (:import (com.dataartisans.flinktraining.exercises.datastream_java.utils TaxiRideSchema)
           (transformations ClojuredGridMatcher ClojuredKeySelector ClojuredRideCounter
                            ClojuredThresholdFilter ClojuredGrid2Coordinates)
           (taxi_stuff TaxiRideTSExtractor))
  (:gen-class))

(def local-zookeeper-host "localhost:2181")
(def local-kafka-broker "localhost:9092")
(def ride-speed-group "RIDE_SPEED_GROUP")
(def time-size 15)
(def time-slide 5)
(def watermark-interval 1000)
(def pop-threshold (int 20))
(def kafka-properties
  (create-properties [["zookeeper.connect" local-zookeeper-host]
                      ["bootstrap.servers" local-kafka-broker]
                      ["group.id" ride-speed-group]
                      ["auto.offset.reset" "earliest"]]))
(def kafka-topic "clojure-rides")

(defn -main [& args]
  (let [exec-env (stream-execution-environment)
        kafka-consumer (create-kafka-consumer kafka-topic (TaxiRideSchema.) kafka-properties)
        timestamped-consumer (assign-timestamp-and-watermarks kafka-consumer (TaxiRideTSExtractor.))]
    (-> (use-event-time exec-env)                           ; threading macro
        (set-auto-watermark-interval watermark-interval)
        (add-source timestamped-consumer)
        (apply-map (ClojuredGridMatcher.))
        (key-by (ClojuredKeySelector.))
        (set-time-window (get-minutes time-size) (get-minutes time-slide))
        (apply-window (ClojuredRideCounter.))
        (apply-filter (ClojuredThresholdFilter. pop-threshold))
        (apply-map (ClojuredGrid2Coordinates.))
        (print-stream))
    (execute exec-env "popular-places-from-kafka-v2")))
