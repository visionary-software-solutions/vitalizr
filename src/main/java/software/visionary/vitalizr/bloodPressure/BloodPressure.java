package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.api.Scalable;
import software.visionary.vitalizr.api.Unit;

public interface BloodPressure extends Scalable {
    @Override
    default Unit getUnit() {
        return MillimetersOfMercury.INSTANCE;
    }
}
