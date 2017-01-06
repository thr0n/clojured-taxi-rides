(ns testframes.popular-places
  (:use bridge.environment bridge.windowing bridge.transformations bridge.datastreams)
  (:import (com.dataartisans.flinktraining.exercises.datastream_java.sources TaxiRideSource)
           (transformations ClojuredGridMatcher ClojuredNYCFilter ClojuredThresholdFilter ClojuredKeySelector ClojuredGrid2Coordinates ClojuredRideCounter)
           (org.apache.flink.streaming.api TimeCharacteristic))
  (:gen-class))

; linux
(def taxi-source "/home/hendrik/dev/github/clojured-taxi-rides/resources/datasets/nycTaxiRides.gz")
; windows
;(def taxi-source "C:\\dev\\github\\clojured-taxi-rides\\resources\\datasets\\nycTaxiRides.gz")
(def max-event-delay 60)                                    ; events are out of order by max 60 seconds
(def serving-speed-factor 600)                              ; events of 10 minutes are served in 1 second
(def time-size 15)
(def time-slide 5)
(def pop-threshold (int 20))

(def exec-env (stream-execution-environment))
(use-event-time exec-env)

(def rides (add-source exec-env (TaxiRideSource. taxi-source max-event-delay serving-speed-factor)))

; NYC only!
(def filtered-rides (apply-filter rides (ClojuredNYCFilter.)))

; Match the grid cells and the event type
(def matched-rides (apply-map filtered-rides (ClojuredGridMatcher.)))

; key the stream by cellId and event type
(def keyed-rides (key-by matched-rides (ClojuredKeySelector.)))

; create a sliding window
(def timed-rides (set-time-window keyed-rides (get-minutes time-size) (get-minutes time-slide)))

; count ride events
(def counted-rides (apply-window timed-rides (ClojuredRideCounter.)))

; filter by threshold
(def filtered-counts (apply-filter counted-rides (ClojuredThresholdFilter. pop-threshold)))

; map the grid cell id back to lon / lat
(def popular-spots (apply-map filtered-counts (ClojuredGrid2Coordinates.)))

(defn -main []
  ;(print-stream popular-spots)
  (write-as-text popular-spots "file:///home//hendrik//pp_clojure.txt")
  (execute exec-env "popular-places"))
