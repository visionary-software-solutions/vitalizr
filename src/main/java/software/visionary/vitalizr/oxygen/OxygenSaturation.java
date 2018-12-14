package software.visionary.vitalizr.oxygen;

import software.visionary.vitalizr.api.Unit;

public enum OxygenSaturation implements Unit {
    INSTANCE;

    @Override
    public String getName() {
        return OxygenSaturation.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "SpO2";
    }
}
