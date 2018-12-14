package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.api.Unit;

public enum Millimolar implements Unit {
    INSTANCE;

    @Override
    public String getName() {
        return Millimolar.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "mM";
    }
}
