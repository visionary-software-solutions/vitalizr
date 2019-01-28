package software.visionary.vitalizr.bodyMassIndex;

import software.visionary.vitalizr.api.Unit;

public enum KilogramsPerMetersSquared implements Unit {
    INSTANCE;

    @Override
    public String getName() {
        return KilogramsPerMetersSquared.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "kg/m^2";
    }
}
