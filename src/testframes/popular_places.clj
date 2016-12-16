(ns testframes.popular-places
  (:use bridge.environment bridge.windowing)
  (:import (com.dataartisans.flinktraining.exercises.datastream_java.sources TaxiRideSource)
           (java_parts ClojuredGridMatcher ClojuredNYCFilter ClojuredThresholdFilter ClojuredKeySelector ClojuredGrid2Coordinates ClojuredRideCounter)
           (org.apache.flink.streaming.api TimeCharacteristic)))

(def taxi-source "..\\..\\resources\\datasets\\nycTaxiRides.gz")
(def max-event-delay 60)        ; events are out of order by max 60 seconds
(def serving-speed-factor 600)  ; events of 10 minutes are served in 1 second
(def time-size 15)
(def time-slide 5)

(def exec-env (stream-execution-environment))
(set-time-characteristic exec-env (TimeCharacteristic/EventTime))

(def rides (add-source exec-env (TaxiRideSource. taxi-source max-event-delay serving-speed-factor)))

; NYC only!
(def filtered-rides (.filter rides (ClojuredNYCFilter.)))

; Match the grid cells and the event type
(def matched-rides (.map filtered-rides (ClojuredGridMatcher.)))

; key the stream by cellId and event type
(def keyed-rides (.keyBy matched-rides (ClojuredKeySelector.)))

; create a sliding window
(def timed-rides (set-time-window keyed-rides (minutes time-size) (minutes time-slide)))

; count ride events
(def counted-rides (.apply timed-rides (ClojuredRideCounter.)))

; filter by threshold
(def filtered-counts (.filter counted-rides (ClojuredThresholdFilter. (int 20))))

; map the grid cell id back to lon / lat
(def popular-spots (.map filtered-counts (ClojuredGrid2Coordinates.)))

(.print popular-spots)
(.execute exec-env "popular-places")
