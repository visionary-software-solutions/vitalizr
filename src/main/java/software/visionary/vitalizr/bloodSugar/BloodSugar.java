package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.api.Scalable;
import software.visionary.vitalizr.api.Unit;

public interface BloodSugar extends Scalable {
    @Override
    default Unit getUnit() {
        return Millimolar.INSTANCE;
    }
}
