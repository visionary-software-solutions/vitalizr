package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.api.Unit;

public enum Pound implements Unit {
    INSTANCE;

    @Override
    public String getName() {
        return Pound.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "lb";
    }
}
