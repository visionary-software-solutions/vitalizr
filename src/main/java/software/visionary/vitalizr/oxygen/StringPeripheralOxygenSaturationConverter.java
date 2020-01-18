package software.visionary.vitalizr.oxygen;

import software.visionary.vitalizr.api.Converter;

import java.util.List;
import java.util.stream.Stream;

public enum StringPeripheralOxygenSaturationConverter implements Converter<Stream<String>, Stream<PeripheralOxygenSaturation>> {
    INSTANCE;

    @Override
    public Stream<PeripheralOxygenSaturation> to(final Stream<String> toConvert) {
        return toConvert.map(PeripheralOxygenSaturationSerializationProxy::parse)
                .flatMap(List::stream)
                .map(PeripheralOxygenSaturationConverter.INSTANCE::from);
    }

    @Override
    public Stream<String> from(final Stream<PeripheralOxygenSaturation> toConvert) {
        return toConvert.map(combined -> PeripheralOxygenSaturationConverter.INSTANCE.to(combined).toString());
    }
}
