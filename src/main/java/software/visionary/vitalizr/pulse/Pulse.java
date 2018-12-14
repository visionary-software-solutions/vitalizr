package software.visionary.vitalizr.pulse;

import software.visionary.vitalizr.api.Scalable;
import software.visionary.vitalizr.api.Unit;

public interface Pulse extends Scalable {
    @Override
    default Unit getUnit() {
        return new HeartbeatsPerMinute();
    }
}
