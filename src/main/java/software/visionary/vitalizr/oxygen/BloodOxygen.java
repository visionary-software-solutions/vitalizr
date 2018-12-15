package software.visionary.vitalizr.oxygen;

import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.api.Vital;

public interface BloodOxygen extends Vital {
    @Override
    default Unit getUnit() {
        return OxygenSaturation.INSTANCE;
    }
}
