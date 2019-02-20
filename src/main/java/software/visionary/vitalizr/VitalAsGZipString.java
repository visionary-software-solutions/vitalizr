package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Vital;
import software.visionary.vitalizr.api.VitalSerializationStrategy;
import software.visionary.vitalizr.bloodPressure.Combined;
import software.visionary.vitalizr.bloodPressure.CombinedBloodPressureSerializationProxy;
import software.visionary.vitalizr.bloodSugar.BloodSugar;
import software.visionary.vitalizr.bloodSugar.BloodSugarSerializationProxy;
import software.visionary.vitalizr.bloodSugar.WholeBloodGlucose;
import software.visionary.vitalizr.bodyMassIndex.BodyMassIndex;
import software.visionary.vitalizr.bodyMassIndex.BodyMassIndexSerializationProxy;
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
import java.util.stream.Stream;

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
        // TODO: For other vitals
        return Stream.of(
                MetricWeightSerializationProxy.stream(entries),
                BodyMassIndexSerializationProxy.stream(entries),
                CombinedBloodPressureSerializationProxy.stream(entries),
                BloodSugarSerializationProxy.stream(entries))
                .flatMap(s -> s)
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
        } else if (v instanceof BodyMassIndex) {
            new WriteObjectAsGZip<>(BodyMassIndexSerializationProxy.fromBodyMassIndex((BodyMassIndex) v), data.toPath()).run();
        } else if (v instanceof Combined) {
            new WriteObjectAsGZip<>(CombinedBloodPressureSerializationProxy.fromBloodPressure((Combined) v), data.toPath()).run();
        } else if (v instanceof WholeBloodGlucose) {
            new WriteObjectAsGZip<>(BloodSugarSerializationProxy.fromBloodSugar((BloodSugar) v), data.toPath()).run();
        }
    }
}
