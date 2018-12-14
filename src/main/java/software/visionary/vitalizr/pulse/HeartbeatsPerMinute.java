package software.visionary.vitalizr.pulse;

import software.visionary.vitalizr.api.Unit;

final class HeartbeatsPerMinute implements Unit {
    @Override
    public String getName() {
        return HeartbeatsPerMinute.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "bpm";
    }
}
