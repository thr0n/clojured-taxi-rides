(ns testframes.ride-cleansing
  (:use bridge.environment bridge.transformations bridge.datastreams)
  (:import (com.dataartisans.flinktraining.exercises.datastream_java.sources TaxiRideSource)
           (transformations ClojuredNYCFilter)))

(def taxi-source "../../resources/datasets/nycTaxiRides.gz")
(def max-event-delay 60)                                    ; events are out of order by max 60 seconds
(def serving-speed-factor 600)                              ; events of 10 minutes are served in 1 second

(def exec-env (stream-execution-environment))
(use-event-time exec-env)

(def rides (add-source exec-env (TaxiRideSource. taxi-source max-event-delay serving-speed-factor)))

(def filtered-rides (apply-filter rides (ClojuredNYCFilter.)))

(print-stream filtered-rides)
(execute exec-env "ride-cleansing")