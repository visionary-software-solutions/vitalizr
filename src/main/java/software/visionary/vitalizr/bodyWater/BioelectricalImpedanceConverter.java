package software.visionary.vitalizr.bodyWater;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.api.Converter;

public enum BioelectricalImpedanceConverter implements Converter<BioelectricalImpedance, BioelectricalImpedanceSerializationProxy> {
    INSTANCE;

    @Override
    public BioelectricalImpedanceSerializationProxy to(final BioelectricalImpedance toConvert) {
        return new BioelectricalImpedanceSerializationProxy(toConvert.observedAt(),
                toConvert.getQuantity().doubleValue(),
                toConvert.getUnit().getSymbol(),
                new LifeformSerializationProxy(toConvert.belongsTo()).toString());
    }

    @Override
    public BioelectricalImpedance from(final BioelectricalImpedanceSerializationProxy toConvert) {
        return new BioelectricalImpedance(toConvert.getObservationTimestamp(), toConvert.getObservedValue(), Human.createPerson(toConvert.getPerson()));
    }
}
