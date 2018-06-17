package visionarysoftwaresolutions.vitalizr.weight;

import visionarysoftwaresolutions.vitalizr.api.Unit;

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
