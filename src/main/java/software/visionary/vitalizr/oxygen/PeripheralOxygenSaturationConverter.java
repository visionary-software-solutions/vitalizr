package software.visionary.vitalizr.oxygen;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.api.Converter;

public enum PeripheralOxygenSaturationConverter implements Converter<PeripheralOxygenSaturation, PeripheralOxygenSaturationSerializationProxy> {
    INSTANCE;

    @Override
    public PeripheralOxygenSaturationSerializationProxy to(final PeripheralOxygenSaturation toConvert) {
        return new PeripheralOxygenSaturationSerializationProxy(toConvert.observedAt(),
                toConvert.getQuantity().intValue(),
                toConvert.getUnit().getSymbol(),
                new LifeformSerializationProxy(toConvert.belongsTo()).toString());
    }

    @Override
    public PeripheralOxygenSaturation from(final PeripheralOxygenSaturationSerializationProxy toConvert) {
        return new PeripheralOxygenSaturation(toConvert.getObservationTimestamp(), toConvert.getObservedValue(), Human.createPerson(toConvert.getPerson()));
    }
}
