(ns java-parts.impl.Clojured-KeySelector
  (:gen-class
    :name java_parts.ClojuredKeySelector
    :implements [java_parts.interfaces.I_Taxi_KeySelector])
  (:import (org.apache.flink.api.java.tuple Tuple2)))

(defn -getKey [this in]
  (Tuple2. (.f0 in) (.f1 in)))
