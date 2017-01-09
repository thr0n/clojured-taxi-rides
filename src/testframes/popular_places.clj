(ns testframes.popular-places
  (:use bridge.environment bridge.windowing bridge.transformations bridge.datastreams)
  (:import (com.dataartisans.flinktraining.exercises.datastream_java.sources TaxiRideSource)
           (transformations ClojuredGridMatcher ClojuredNYCFilter ClojuredThresholdFilter ClojuredKeySelector
                            ClojuredGrid2Coordinates ClojuredRideCounter))
  (:gen-class))

; linux
;(def taxi-source "/home/hendrik/dev/github/clojured-taxi-rides/resources/datasets/nycTaxiRides.gz")
; windows
(def taxi-source "C:\\dev\\github\\clojured-taxi-rides\\resources\\datasets\\nycTaxiRides.gz")
(def max-event-delay 60)                                    ; events are out of order by max 60 seconds
(def serving-speed-factor 600)                              ; events of 10 minutes are served in 1 second
(def time-size 15)
(def time-slide 5)
(def pop-threshold (int 20))

(defn -main [& args]
  (let [exec-env (stream-execution-environment)]
    (-> (use-event-time exec-env)                           ; threading macro
        (add-source (TaxiRideSource. taxi-source max-event-delay serving-speed-factor))
        (apply-filter (ClojuredNYCFilter.))
        (apply-map (ClojuredGridMatcher.))
        (key-by (ClojuredKeySelector.))
        (set-time-window (get-minutes time-size) (get-minutes time-slide))
        (apply-window (ClojuredRideCounter.))
        (apply-filter (ClojuredThresholdFilter. pop-threshold))
        (apply-map (ClojuredGrid2Coordinates.))
        (print-stream)
        )
    (execute exec-env "popular-places-v2")))
