package software.visionary.vitalizr.bodyTemperature;

import software.visionary.vitalizr.api.Unit;

public enum Fahrenheit implements Unit {
    INSTANCE;

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "F";
    }
}
