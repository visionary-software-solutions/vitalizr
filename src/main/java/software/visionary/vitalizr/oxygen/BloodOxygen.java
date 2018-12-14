package software.visionary.vitalizr.oxygen;

import software.visionary.vitalizr.api.Scalable;
import software.visionary.vitalizr.api.Unit;

public interface BloodOxygen extends Scalable {
    @Override
    default Unit getUnit() {
        return new OxygenSaturation();
    }
}
