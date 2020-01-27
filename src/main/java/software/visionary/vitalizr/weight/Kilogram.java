package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.api.Unit;

public enum Kilogram implements Unit {
    INSTANCE;

    @Override
    public String getName() {
        return Kilogram.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "kg";
    }
}
