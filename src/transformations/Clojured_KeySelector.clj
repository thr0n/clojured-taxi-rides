(ns transformations.Clojured-KeySelector
  (:gen-class
    :name transformations.ClojuredKeySelector
    :implements [java_interfaces.I_Taxi_KeySelector])
  (:import (org.apache.flink.api.java.tuple Tuple2)))

(defn -getKey [this in]
  (Tuple2. (.f0 in) (.f1 in)))
