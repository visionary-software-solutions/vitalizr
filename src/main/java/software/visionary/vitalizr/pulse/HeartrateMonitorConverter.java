package software.visionary.vitalizr.pulse;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.LifeformSerializationProxy;
import software.visionary.vitalizr.api.Converter;

public enum HeartrateMonitorConverter implements Converter<HeartrateMonitor, HeartrateMonitorSerializationProxy> {
    INSTANCE;

    @Override
    public HeartrateMonitorSerializationProxy to(final HeartrateMonitor toConvert) {
        return new HeartrateMonitorSerializationProxy(toConvert.observedAt(),
                toConvert.getQuantity().intValue(),
                toConvert.getUnit().getSymbol(),
                new LifeformSerializationProxy(toConvert.belongsTo()).toString());
    }

    @Override
    public HeartrateMonitor from(final HeartrateMonitorSerializationProxy toConvert) {
        return new HeartrateMonitor(toConvert.getObservationTimestamp(), toConvert.getObservedValue(), Human.createPerson(toConvert.getPerson()));
    }
}
