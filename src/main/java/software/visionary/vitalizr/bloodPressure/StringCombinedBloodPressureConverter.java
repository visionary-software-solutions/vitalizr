package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.api.Converter;

import java.util.List;
import java.util.stream.Stream;

public enum StringCombinedBloodPressureConverter implements Converter<Stream<String>, Stream<Combined>> {
    INSTANCE;

    @Override
    public Stream<Combined> to(final Stream<String> toConvert) {
        return toConvert.map(CombinedBloodPressureSerializationProxy::parse)
                .flatMap(List::stream)
                .map(CombinedBloodPressureConverter.INSTANCE::from);
    }

    @Override
    public Stream<String> from(final Stream<Combined> toConvert) {
        return toConvert.map(combined -> CombinedBloodPressureConverter.INSTANCE.to(combined).toString());
    }
}
