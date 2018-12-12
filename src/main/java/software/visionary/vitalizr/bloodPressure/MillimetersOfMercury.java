package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.api.Unit;

public final class MillimetersOfMercury implements Unit {
    @Override
    public String getName() {
        return MillimetersOfMercury.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "mm Hg";
    }
}
