package java_interfaces;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * Created by ht on 16.12.2016.
 */
public interface I_Taxi_KeySelector extends KeySelector<Tuple2<Integer, Boolean>, Tuple2<Integer, Boolean>> {
}
