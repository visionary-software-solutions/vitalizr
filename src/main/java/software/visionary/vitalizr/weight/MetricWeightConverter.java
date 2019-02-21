package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.api.Converter;

public enum MetricWeightConverter implements Converter<MetricWeight, MetricWeightSerializationProxy> {
    INSTANCE;

    @Override
    public MetricWeightSerializationProxy to(final MetricWeight toConvert) {
        return new MetricWeightSerializationProxy(toConvert.observedAt(),
                (byte) (toConvert.getQuantity().doubleValue() / 1000),
                toConvert.getUnit().getSymbol(),
                new LifeformSerializationProxy(toConvert.belongsTo()).toString());
    }

    @Override
    public MetricWeight from(final MetricWeightSerializationProxy toConvert) {
        return MetricWeight.inKilograms(toConvert.getObservedValue(), toConvert.getObservationTimestamp(), Human.createPerson(toConvert.getPerson()));
    }
}
