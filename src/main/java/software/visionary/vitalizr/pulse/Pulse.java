package software.visionary.vitalizr.pulse;

import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.api.Vital;

public interface Pulse extends Vital {
    @Override
    default Unit getUnit() {
        return HeartbeatsPerMinute.INSTANCE;
    }
}
