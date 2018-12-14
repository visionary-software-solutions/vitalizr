package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.api.Unit;

public final class Millimolar implements Unit {
    @Override
    public String getName() {
        return Millimolar.class.getSimpleName();
    }

    @Override
    public String getSymbol() {
        return "mM";
    }
}
