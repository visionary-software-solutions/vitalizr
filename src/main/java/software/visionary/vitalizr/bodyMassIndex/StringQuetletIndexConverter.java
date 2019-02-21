package software.visionary.vitalizr.bodyMassIndex;

import software.visionary.vitalizr.api.Converter;

import java.util.List;
import java.util.stream.Stream;

public enum StringQuetletIndexConverter implements Converter<Stream<String>, Stream<QueteletIndex>> {
    INSTANCE;

    @Override
    public Stream<QueteletIndex> to(final Stream<String> toConvert) {
        return toConvert.map(QuetletIndexSerializationProxy::parse)
                .flatMap(List::stream)
                .map(QuetletIndexConverter.INSTANCE::from);
    }

    @Override
    public Stream<String> from(final Stream<QueteletIndex> toConvert) {
        return toConvert.map(combined -> QuetletIndexConverter.INSTANCE.to(combined).toString());
    }
}
