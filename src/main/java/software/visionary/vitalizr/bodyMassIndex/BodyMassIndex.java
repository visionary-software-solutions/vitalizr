package software.visionary.vitalizr.bodyMassIndex;

import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.api.Vital;

public interface BodyMassIndex extends Vital {
    @Override
    default Unit getUnit() {
        return KilogramsPerMetersSquared.INSTANCE;
    }
}
