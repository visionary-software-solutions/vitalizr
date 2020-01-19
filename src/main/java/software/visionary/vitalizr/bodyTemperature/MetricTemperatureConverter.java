package software.visionary.vitalizr.bodyTemperature;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.api.Converter;

public enum MetricTemperatureConverter implements Converter<MetricTemperature, MetricTemperatureSerializationProxy> {
    INSTANCE;

    @Override
    public MetricTemperatureSerializationProxy to(final MetricTemperature toConvert) {
        return new MetricTemperatureSerializationProxy(toConvert.observedAt(),
                toConvert.getQuantity().doubleValue(),
                toConvert.getUnit().getSymbol(),
                new LifeformSerializationProxy(toConvert.belongsTo()).toString());
    }

    @Override
    public MetricTemperature from(final MetricTemperatureSerializationProxy toConvert) {
        return new MetricTemperature(Human.createPerson(toConvert.getPerson()), toConvert.getObservedValue(), toConvert.getObservationTimestamp());
    }
}
