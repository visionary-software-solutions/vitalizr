package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.api.Unit;

public enum MilligramsPerDecilitre implements Unit {
    INSTANCE;

    @Override
    public String getName() {
        return MilligramsPerDecilitre.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "mg/dL";
    }
}
