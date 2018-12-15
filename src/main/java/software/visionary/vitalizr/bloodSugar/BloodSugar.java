package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.api.Vital;

public interface BloodSugar extends Vital {
    @Override
    default Unit getUnit() {
        return Millimolar.INSTANCE;
    }
}
