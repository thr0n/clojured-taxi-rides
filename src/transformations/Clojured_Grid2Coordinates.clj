(ns transformations.Clojured-Grid2Coordinates
  (:gen-class
    :name transformations.ClojuredGrid2Coordinates
    :implements [java_interfaces.I_Taxi_Grid2Coordinates])
  (:import (org.apache.flink.api.java.tuple Tuple5)
           (com.dataartisans.flinktraining.exercises.datastream_java.utils GeoUtils)))

(defn -map [this cellCount]
  (Tuple5. (GeoUtils/getGridCellCenterLon (.f0 cellCount))
           (GeoUtils/getGridCellCenterLat (.f0 cellCount))
           (.f1 cellCount)
           (.f2 cellCount)
           (.f3 cellCount)))
