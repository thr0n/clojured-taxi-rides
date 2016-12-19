(ns transformations.Clojured-ThresholdFilter
  (:gen-class
    :name transformations.ClojuredThresholdFilter
    :implements [java_interfaces.I_Taxi_ThresholdFilter
                 java.io.Serializable]
    :state state
    :constructors {[Integer] []}
    :init init))

(defn -init [threshold-value]
  [[] threshold-value])

(defn -filter [this count]
  ;(>= (.f3 count) 20))
  (>= (.f3 count) (.state this)))
