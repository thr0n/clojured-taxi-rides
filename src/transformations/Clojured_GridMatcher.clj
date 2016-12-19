(ns transformations.Clojured-GridMatcher
  (:gen-class
    :name transformations.ClojuredGridMatcher
    :implements [java_interfaces.I_Taxi_GridMatcher])
  (:import (com.dataartisans.flinktraining.exercises.datastream_java.utils GeoUtils)
           (org.apache.flink.api.java.tuple Tuple2)))

(defn map-to-grid-cell [lon lat]
  (GeoUtils/mapToGridCell lon lat))

(defn -map [this ride]
  (if (true? (.-isStart ride))
    (Tuple2. (map-to-grid-cell (.-startLon ride) (.-startLat ride)) true)
    (Tuple2. (map-to-grid-cell (.-endLon ride) (.-endLat ride)) false)))
