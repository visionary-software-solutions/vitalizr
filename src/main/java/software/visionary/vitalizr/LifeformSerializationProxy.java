package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Lifeform;

public class LifeformSerializationProxy {
    private final String asAString;

    public LifeformSerializationProxy(final Lifeform toSerialize) {
        if (toSerialize instanceof Human) {
            asAString = toSerialize.toString();
        } else {
            throw new UnsupportedOperationException("We only know how to process Humans!");
        }
    }

    @Override
    public String toString() {
        return asAString;
    }
}
