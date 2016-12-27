(ns testframes.ride-cleansing
  (:require [bridge.environment :refer [stream-execution-environment use-event-time add-source execute]]
            [bridge.transformations :refer [apply-filter]]
            [bridge.datastreams :refer [print-stream write-as-text]])
  (:import (com.dataartisans.flinktraining.exercises.datastream_java.sources TaxiRideSource)
           (transformations ClojuredNYCFilter))
  (:gen-class :main))

; linux
;(def taxi-source "/home/hendrik/dev/github/clojured-taxi-rides/resources/datasets/nycTaxiRides.gz")
; windows
(def taxi-source "C:\\dev\\github\\clojured-taxi-rides\\resources\\datasets\\nycTaxiRides.gz")

(def max-event-delay 60)                                    ; events are out of order by max 60 seconds
(def serving-speed-factor 600)                              ; events of 10 minutes are served in 1 second

(def exec-env (stream-execution-environment))
(use-event-time exec-env)

(def rides (add-source exec-env (TaxiRideSource. taxi-source max-event-delay serving-speed-factor)))

(def filtered-rides (apply-filter rides (ClojuredNYCFilter.)))

(defn -main [& args]
  (print-stream filtered-rides)
  ;(write-as-text filtered-rides "file:\\\\C:\\output")
  (execute exec-env "ride-cleansing"))

