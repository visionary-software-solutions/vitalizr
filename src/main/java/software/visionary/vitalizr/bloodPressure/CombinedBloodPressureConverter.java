package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.Fraction;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.api.Converter;

public enum CombinedBloodPressureConverter implements Converter<Combined, CombinedBloodPressureSerializationProxy> {
    INSTANCE;

    @Override
    public CombinedBloodPressureSerializationProxy to(final Combined toConvert) {
        return new CombinedBloodPressureSerializationProxy(toConvert.observedAt(),
                (Fraction) toConvert.getQuantity(),
                toConvert.getUnit().getSymbol(),
                new LifeformSerializationProxy(toConvert.belongsTo()).toString());
    }

    @Override
    public Combined from(final CombinedBloodPressureSerializationProxy toConvert) {
        return Combined.systolicAndDiastolicBloodPressure(toConvert.getObservationTimestamp(),
                toConvert.getObservedValue().getNumerator().intValue(),
                toConvert.getObservedValue().getDenominator().intValue(),
                Human.createPerson(toConvert.getPerson()));
    }
}
