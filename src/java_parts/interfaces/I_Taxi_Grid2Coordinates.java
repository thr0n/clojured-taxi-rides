package java_parts.interfaces;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.tuple.Tuple5;

/**
 * Created by ht on 16.12.2016.
 */
public interface I_Taxi_Grid2Coordinates extends MapFunction<Tuple4<Integer, Long, Boolean, Integer>,
                Tuple5<Float, Float, Long, Boolean, Integer>> {
}
