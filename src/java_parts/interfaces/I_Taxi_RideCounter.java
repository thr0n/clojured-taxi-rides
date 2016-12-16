package java_parts.interfaces;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

/**
 * Created by ht on 16.12.2016.
 */
public interface I_Taxi_RideCounter extends WindowFunction<
        Tuple2<Integer, Boolean>, // input type
        Tuple4<Integer, Long, Boolean, Integer>, // output type
        Tuple, // key tuple
        TimeWindow> { // window type
}
