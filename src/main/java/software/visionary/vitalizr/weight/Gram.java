package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.api.Unit;

public class Gram implements Unit {
    @Override
    public String getName() {
        return Gram.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "g";
    }
}
