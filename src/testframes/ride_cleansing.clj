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

(defn -main [& args]
  (let [exec-env (stream-execution-environment)]
    (-> (use-event-time exec-env)                           ; threading macro
        (add-source (TaxiRideSource. taxi-source max-event-delay serving-speed-factor))
        (apply-filter (ClojuredNYCFilter.))
        (print-stream)
        )
    (execute exec-env "ride-cleansing-v2")))
