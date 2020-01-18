package software.visionary.vitalizr.bodyTemperature;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.api.Converter;

public enum ImperialTemperatureConverter implements Converter<ImperialTemperature, ImperialTemperatureSerializationProxy> {
    INSTANCE;

    @Override
    public ImperialTemperatureSerializationProxy to(final ImperialTemperature toConvert) {
        return new ImperialTemperatureSerializationProxy(toConvert.observedAt(),
                toConvert.getQuantity().doubleValue(),
                toConvert.getUnit().getSymbol(),
                new LifeformSerializationProxy(toConvert.belongsTo()).toString());
    }

    @Override
    public ImperialTemperature from(final ImperialTemperatureSerializationProxy toConvert) {
        return new ImperialTemperature(Human.createPerson(toConvert.getPerson()), toConvert.getObservedValue(), toConvert.getObservationTimestamp());
    }
}
