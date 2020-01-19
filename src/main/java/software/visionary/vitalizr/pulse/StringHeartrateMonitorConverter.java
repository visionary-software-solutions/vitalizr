package software.visionary.vitalizr.pulse;

import software.visionary.vitalizr.api.Converter;

import java.util.List;
import java.util.stream.Stream;

public enum StringHeartrateMonitorConverter implements Converter<Stream<String>, Stream<HeartrateMonitor>> {
    INSTANCE;

    @Override
    public Stream<HeartrateMonitor> to(final Stream<String> toConvert) {
        return toConvert.map(HeartrateMonitorSerializationProxy::parse)
                .flatMap(List::stream)
                .map(HeartrateMonitorConverter.INSTANCE::from);
    }

    @Override
    public Stream<String> from(final Stream<HeartrateMonitor> toConvert) {
        return toConvert.map(combined -> HeartrateMonitorConverter.INSTANCE.to(combined).toString());
    }
}
