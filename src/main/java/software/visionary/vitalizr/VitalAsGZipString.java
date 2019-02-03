package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Vital;
import software.visionary.vitalizr.api.VitalSerializationStrategy;
import software.visionary.vitalizr.serialization.GZipFiles;
import software.visionary.vitalizr.serialization.WriteObjectAsGZip;
import software.visionary.vitalizr.weight.MetricWeight;
import software.visionary.vitalizr.weight.MetricWeightSerializationProxy;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

enum VitalAsGZipString implements VitalSerializationStrategy<File> {
    INSTANCE;

    @Override
    public Collection<Vital> deserialize(final File data) {
        try {
            final List<String> entries = GZipFiles.slurpGZippedFile(data.toPath(), StandardCharsets.UTF_8);
            return convert(entries);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Vital> convert(final List<String> entries) {
        // TODO: For other vitals besides MetricWeight
        return MetricWeightSerializationProxy.getMetricWeightStream(entries)
                .collect(Collectors.toList());
    }

    @Override
    public void serialize(final Collection<Vital> toWrite, final File data) {
        toWrite.forEach(v -> writeTo(data, v));
    }

    private static void writeTo(final File data, final Vital v) {
        // TODO: Add support for other Vitals
        if (v instanceof MetricWeight) {
            new WriteObjectAsGZip<>(MetricWeightSerializationProxy.fromMetricWeight((MetricWeight) v), data.toPath()).run();
        }
    }
}
