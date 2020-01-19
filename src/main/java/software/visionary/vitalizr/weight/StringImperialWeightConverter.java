package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.api.Converter;

import java.util.List;
import java.util.stream.Stream;

public enum StringImperialWeightConverter implements Converter<Stream<String>, Stream<ImperialWeight>> {
    INSTANCE;

    @Override
    public Stream<ImperialWeight> to(final Stream<String> toConvert) {
        return toConvert.map(ImperialWeightSerializationProxy::parse)
                .flatMap(List::stream)
                .map(ImperialWeightConverter.INSTANCE::from);
    }

    @Override
    public Stream<String> from(final Stream<ImperialWeight> toConvert) {
        return toConvert.map(combined -> ImperialWeightConverter.INSTANCE.to(combined).toString());
    }
}
