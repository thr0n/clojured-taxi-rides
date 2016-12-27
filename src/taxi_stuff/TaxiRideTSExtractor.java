package taxi_stuff;

import com.dataartisans.flinktraining.exercises.datastream_java.datatypes.TaxiRide;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by ht on 22.12.2016.
 */
public class TaxiRideTSExtractor extends BoundedOutOfOrdernessTimestampExtractor<TaxiRide> {
    public static final int MAX_EVENT_DELAY = 60;

    public TaxiRideTSExtractor() {
        super(Time.seconds(MAX_EVENT_DELAY));
    }

    @Override
    public long extractTimestamp(TaxiRide ride) {
        if (ride.isStart) {
            return ride.startTime.getMillis();
        }
        else {
            return ride.endTime.getMillis();
        }
    }
}