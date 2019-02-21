package software.visionary.vitalizr.bloodSugar;

import software.visionary.vitalizr.api.Converter;

import java.util.List;
import java.util.stream.Stream;

public enum StringWholeBloodGlucoseConverter implements Converter<Stream<String>, Stream<WholeBloodGlucose>> {
    INSTANCE;

    @Override
    public Stream<WholeBloodGlucose> to(final Stream<String> toConvert) {
        return toConvert.map(WholeBloodGlucoseSerializationProxy::parse)
                .flatMap(List::stream)
                .map(WholeBloodGlucoseConverter.INSTANCE::from);
    }

    @Override
    public Stream<String> from(final Stream<WholeBloodGlucose> toConvert) {
        return toConvert.map(combined -> WholeBloodGlucoseConverter.INSTANCE.to(combined).toString());
    }
}
