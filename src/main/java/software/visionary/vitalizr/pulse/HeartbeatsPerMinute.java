package software.visionary.vitalizr.pulse;

import software.visionary.vitalizr.api.Unit;

public enum HeartbeatsPerMinute implements Unit {
    INSTANCE;

    @Override
    public String getName() {
        return HeartbeatsPerMinute.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "bpm";
    }
}
