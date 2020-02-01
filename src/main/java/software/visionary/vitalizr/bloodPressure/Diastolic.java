package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.AbstractVital;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Unit;

import java.time.Instant;

public final class Diastolic extends AbstractVital implements BloodPressure {

    public Diastolic(final Instant observed, final Number number, final Lifeform lifeform) {
        super(observed, number, lifeform);
    }

    @Override
    public Unit getUnit() {
        return MillimetersOfMercury.INSTANCE;
    }
}
