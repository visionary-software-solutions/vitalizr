package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.api.Vital;

public interface BloodPressure extends Vital {
    @Override
    default Unit getUnit() {
        return MillimetersOfMercury.INSTANCE;
    }
}
