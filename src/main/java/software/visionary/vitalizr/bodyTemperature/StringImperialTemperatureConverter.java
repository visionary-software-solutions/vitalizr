package software.visionary.vitalizr.bodyTemperature;

import software.visionary.vitalizr.api.Converter;

import java.util.List;
import java.util.stream.Stream;

public enum StringImperialTemperatureConverter implements Converter<Stream<String>, Stream<ImperialTemperature>> {
    INSTANCE;

    @Override
    public Stream<ImperialTemperature> to(final Stream<String> toConvert) {
        return toConvert.map(ImperialTemperatureSerializationProxy::parse)
                .flatMap(List::stream)
                .map(ImperialTemperatureConverter.INSTANCE::from);
    }

    @Override
    public Stream<String> from(final Stream<ImperialTemperature> toConvert) {
        return toConvert.map(combined -> ImperialTemperatureConverter.INSTANCE.to(combined).toString());
    }
}
