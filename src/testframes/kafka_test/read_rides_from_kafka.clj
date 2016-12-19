(ns testframes.kafka-test.read-rides-from-kafka
  (:use bridge.environment bridge.datastreams bridge.connectors.kafka bridge.transformations bridge.windowing bridge.window-timing)
  (:import (com.dataartisans.flinktraining.exercises.datastream_java.utils TaxiRideSchema)
           (transformations ClojuredNYCFilter ClojuredGridMatcher ClojuredKeySelector ClojuredRideCounter ClojuredThresholdFilter ClojuredGrid2Coordinates)
           (taxi_stuff TaxiRideTSExtractor)))

(def local-zookeeper-host "localhost:2181")
(def local-kafka-broker "localhost:9092")
(def ride-speed-group "RIDE_SPEED_GROUP")
(def max-event-delay 60)        ; events are out of order by max 60 seconds
(def time-size 15)
(def time-slide 5)
(def watermark-interval 1000)
(def pop-threshold (int 20))


(def exec-env (stream-execution-environment))
(use-event-time exec-env)
(set-auto-watermark-interval exec-env watermark-interval)

(def kafka-properties
  (create-properties [["zookeeper.connect" local-zookeeper-host]
                      ["bootstrap.servers" local-kafka-broker]
                      ["group.id" ride-speed-group]
                      ["auto.offset.reset" "earliest"]]))

(def kafka-consumer
  (create-kafka-consumer "cleansed-rides" (TaxiRideSchema.) kafka-properties))
(assign-timestamp-and-watermarks kafka-consumer (TaxiRideTSExtractor.))

(def rides (add-source exec-env kafka-consumer))

; NYC only!
(def filtered-rides (apply-filter rides (ClojuredNYCFilter.)))

; Match the grid cells and the event type
(def matched-rides (apply-map filtered-rides (ClojuredGridMatcher.)))

; key the stream by cellId and event type
(def keyed-rides (key-by matched-rides (ClojuredKeySelector.)))

; create a sliding window
(def timed-rides (set-time-window keyed-rides (minutes time-size) (minutes time-slide)))

; count ride events
(def counted-rides (apply-window timed-rides (ClojuredRideCounter.)))

; filter by threshold
(def filtered-counts (apply-filter counted-rides (ClojuredThresholdFilter. pop-threshold)))

; map the grid cell id back to lon / lat
(def popular-spots (apply-map filtered-counts (ClojuredGrid2Coordinates.)))

(print-stream popular-spots)

(execute exec-env "read-popular-places-from-kafka")