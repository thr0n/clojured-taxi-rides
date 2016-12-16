(ns java-parts.impl.Clojured-ThresholdFilter
  (:gen-class
    :name java_parts.ClojuredThresholdFilter
    :implements [java_parts.interfaces.I_Taxi_ThresholdFilter
                 java.io.Serializable]
    :state state
    :constructors {[Integer] []}
    :init init))

(defn -init [threshold-value]
  [[] threshold-value])

(defn -filter [this count]
  ;(>= (.f3 count) 20))
  (>= (.f3 count) (.state this)))
