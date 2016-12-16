package java_parts.interfaces;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.tuple.Tuple4;

/**
 * Created by ht on 16.12.2016.
 */
public interface I_Taxi_ThresholdFilter extends FilterFunction<Tuple4<Integer, Long, Boolean, Integer>> {
}
