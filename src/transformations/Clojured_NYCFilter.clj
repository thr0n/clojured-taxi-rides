(ns transformations.Clojured-NYCFilter
  (:gen-class
    :name transformations.ClojuredNYCFilter
    :implements [java_interfaces.I_Taxi_NYCFilter])
  (:import (com.dataartisans.flinktraining.exercises.datastream_java.utils GeoUtils)))

(defn -filter [this ride]
  (and (GeoUtils/isInNYC (.-startLon ride) (.-startLat ride))
       (GeoUtils/isInNYC (.-endLon ride) (.-endLat ride))))
