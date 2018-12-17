package software.visionary.vitalizr.bodyTemperature;

import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.api.Vital;

public interface BodyTemperature extends Vital {
    @Override
    default Unit getUnit() {
        return Celsius.INSTANCE;
    }
}
