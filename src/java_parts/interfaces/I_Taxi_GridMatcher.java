package java_parts.interfaces;

import com.dataartisans.flinktraining.exercises.datastream_java.datatypes.TaxiRide;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.elasticsearch.search.aggregations.support.format.ValueParser;

/**
 * Created by ht on 14.12.2016.
 */
public interface I_Taxi_GridMatcher extends MapFunction<TaxiRide, Tuple2<Integer, Boolean>> {
}
