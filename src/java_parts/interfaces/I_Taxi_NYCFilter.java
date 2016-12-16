package java_parts.interfaces;

import com.dataartisans.flinktraining.exercises.datastream_java.datatypes.TaxiRide;
import org.apache.flink.api.common.functions.FilterFunction;

/**
 * Created by ht on 14.12.2016.
 */
public interface I_Taxi_NYCFilter extends FilterFunction<TaxiRide> {
}
