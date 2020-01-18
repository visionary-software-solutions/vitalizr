package software.visionary.vitalizr.bodyWater;

import software.visionary.vitalizr.api.Converter;
import java.util.List;
import java.util.stream.Stream;

public enum StringBioelectricalImpedanceConverter implements Converter<Stream<String>, Stream<BioelectricalImpedance>> {
    INSTANCE;

    @Override
    public Stream<BioelectricalImpedance> to(final Stream<String> toConvert) {
        return toConvert.map(BioelectricalImpedanceSerializationProxy::parse)
                .flatMap(List::stream)
                .map(BioelectricalImpedanceConverter.INSTANCE::from);
    }

    @Override
    public Stream<String> from(final Stream<BioelectricalImpedance> toConvert) {
        return toConvert.map(combined -> BioelectricalImpedanceConverter.INSTANCE.to(combined).toString());
    }
}
