package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.api.Unit;

public final class MilligramsPerDecilitre implements Unit {
    @Override
    public String getName() {
        return MilligramsPerDecilitre.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "mg/dL";
    }
}
