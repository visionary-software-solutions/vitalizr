package software.visionary.vitalizr.oxygen;

import software.visionary.vitalizr.api.Unit;

final class OxygenSaturation implements Unit {
    @Override
    public String getName() {
        return OxygenSaturation.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "SpO2";
    }
}
