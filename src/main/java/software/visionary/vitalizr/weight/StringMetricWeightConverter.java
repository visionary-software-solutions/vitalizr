package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.api.Converter;

import java.util.List;
import java.util.stream.Stream;

public enum StringMetricWeightConverter implements Converter<Stream<String>, Stream<MetricWeight>> {
    INSTANCE;

    @Override
    public Stream<MetricWeight> to(final Stream<String> toConvert) {
        return toConvert.map(MetricWeightSerializationProxy::parse)
                .flatMap(List::stream)
                .map(MetricWeightConverter.INSTANCE::from);
    }

    @Override
    public Stream<String> from(final Stream<MetricWeight> toConvert) {
        return toConvert.map(combined -> MetricWeightConverter.INSTANCE.to(combined).toString());
    }
}
