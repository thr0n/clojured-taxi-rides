(ns java-parts.impl.Clojured-Grid2Coordinates
  (:gen-class
    :name java_parts.ClojuredGrid2Coordinates
    :implements [java_parts.interfaces.I_Taxi_Grid2Coordinates])
  (:import (org.apache.flink.api.java.tuple Tuple5)
           (com.dataartisans.flinktraining.exercises.datastream_java.utils GeoUtils)))

(defn -map [this cellCount]
  (Tuple5. (GeoUtils/getGridCellCenterLon (.f0 cellCount))
           (GeoUtils/getGridCellCenterLat (.f0 cellCount))
           (.f1 cellCount)
           (.f2 cellCount)
           (.f3 cellCount)))
