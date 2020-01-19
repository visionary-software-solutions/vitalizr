package software.visionary.vitalizr.bodyTemperature;

import software.visionary.vitalizr.api.Converter;

import java.util.List;
import java.util.stream.Stream;

public enum StringMetricTemperatureConverter implements Converter<Stream<String>, Stream<MetricTemperature>> {
    INSTANCE;

    @Override
    public Stream<MetricTemperature> to(final Stream<String> toConvert) {
        return toConvert.map(MetricTemperatureSerializationProxy::parse)
                .flatMap(List::stream)
                .map(MetricTemperatureConverter.INSTANCE::from);
    }

    @Override
    public Stream<String> from(final Stream<MetricTemperature> toConvert) {
        return toConvert.map(combined -> MetricTemperatureConverter.INSTANCE.to(combined).toString());
    }
}
