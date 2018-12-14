package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.api.Unit;

public enum MillimetersOfMercury implements Unit {
    INSTANCE;

    @Override
    public String getName() {
        return MillimetersOfMercury.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "mm Hg";
    }
}
