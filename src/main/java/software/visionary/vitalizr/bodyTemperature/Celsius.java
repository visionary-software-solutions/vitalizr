package software.visionary.vitalizr.bodyTemperature;

import software.visionary.vitalizr.api.Unit;

public enum Celsius implements Unit {
    INSTANCE;

    @Override
    public String getName() {
        return Celsius.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "C";
    }
}
