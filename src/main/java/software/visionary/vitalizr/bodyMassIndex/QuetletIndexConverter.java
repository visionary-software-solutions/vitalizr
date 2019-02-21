package software.visionary.vitalizr.bodyMassIndex;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.api.Converter;

public enum QuetletIndexConverter implements Converter<QueteletIndex, QuetletIndexSerializationProxy> {
    INSTANCE;

    @Override
    public QuetletIndexSerializationProxy to(final QueteletIndex toConvert) {
        return new QuetletIndexSerializationProxy(toConvert.observedAt(),
                toConvert.getQuantity().doubleValue(),
                toConvert.getUnit().getSymbol(),
                new LifeformSerializationProxy(toConvert.belongsTo()).toString());
    }

    @Override
    public QueteletIndex from(final QuetletIndexSerializationProxy toConvert) {
        return new QueteletIndex(toConvert.getObservationTimestamp(), toConvert.getObservedValue(), Human.createPerson(toConvert.getPerson()));
    }
}
