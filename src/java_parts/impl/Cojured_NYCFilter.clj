(ns java-parts.impl.Cojured-NYCFilter
  (:gen-class
    :name java_parts.ClojuredNYCFilter
    :implements [java_parts.interfaces.I_Taxi_NYCFilter])
  (:import (com.dataartisans.flinktraining.exercises.datastream_java.utils GeoUtils)))

(defn -filter [this ride]
  (and (GeoUtils/isInNYC (.-startLon ride) (.-startLat ride))
       (GeoUtils/isInNYC (.-endLon ride) (.-endLat ride))))
