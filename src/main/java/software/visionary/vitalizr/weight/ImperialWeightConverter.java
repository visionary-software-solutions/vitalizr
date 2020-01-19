package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.api.Converter;

public enum ImperialWeightConverter implements Converter<ImperialWeight, ImperialWeightSerializationProxy> {
    INSTANCE;

    @Override
    public ImperialWeightSerializationProxy to(final ImperialWeight toConvert) {
        return new ImperialWeightSerializationProxy(toConvert.observedAt(),
                toConvert.getQuantity().doubleValue(),
                toConvert.getUnit().getSymbol(),
                new LifeformSerializationProxy(toConvert.belongsTo()).toString());
    }

    @Override
    public ImperialWeight from(final ImperialWeightSerializationProxy toConvert) {
        return new ImperialWeight(toConvert.getObservationTimestamp(), toConvert.getObservedValue(), Human.createPerson(toConvert.getPerson()));
    }
}
