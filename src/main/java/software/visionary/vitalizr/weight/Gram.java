package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.api.Unit;

public enum Gram implements Unit {
    INSTANCE;

    @Override
    public String getName() {
        return Gram.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "g";
    }
}
