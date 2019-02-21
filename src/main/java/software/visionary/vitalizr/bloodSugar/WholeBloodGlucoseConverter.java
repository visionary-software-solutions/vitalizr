package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.api.Converter;

public enum WholeBloodGlucoseConverter implements Converter<WholeBloodGlucose, WholeBloodGlucoseSerializationProxy> {
    INSTANCE;

    @Override
    public WholeBloodGlucoseSerializationProxy to(final WholeBloodGlucose toConvert) {
        return new WholeBloodGlucoseSerializationProxy(toConvert.observedAt(),
                toConvert.getQuantity().intValue(),
                toConvert.getUnit().getSymbol(),
                new LifeformSerializationProxy(toConvert.belongsTo()).toString());
    }

    @Override
    public WholeBloodGlucose from(final WholeBloodGlucoseSerializationProxy toConvert) {
        return new WholeBloodGlucose(toConvert.getObservationTimestamp(), toConvert.getObservedValue(), Human.createPerson(toConvert.getPerson()));
    }
}
