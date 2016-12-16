(ns java-parts.impl.Clojured-RideCounter
  (:gen-class
    :name java_parts.ClojuredRideCounter
    :implements [java_parts.interfaces.I_Taxi_RideCounter])
  (:import (org.apache.flink.api.java.tuple Tuple4)))

(defn -apply [this key window values out]
  (.collect out
            (Tuple4. (.f0 key) (.getEnd window) (.f1 key) (count values)))
  )
