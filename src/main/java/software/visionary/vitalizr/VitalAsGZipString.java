package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Vital;
import software.visionary.vitalizr.api.VitalSerializationStrategy;
import software.visionary.vitalizr.bloodPressure.Combined;
import software.visionary.vitalizr.bloodSugar.WholeBloodGlucose;
import software.visionary.vitalizr.bodyFat.BioelectricalImpedance;
import software.visionary.vitalizr.bodyMassIndex.QueteletIndex;
import software.visionary.vitalizr.bodyTemperature.*;
import software.visionary.vitalizr.oxygen.PeripheralOxygenSaturation;
import software.visionary.vitalizr.pulse.HeartrateMonitor;
import software.visionary.vitalizr.serialization.GZipFiles;
import software.visionary.vitalizr.serialization.WriteObjectAsGZip;
import software.visionary.vitalizr.weight.*;

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
        // TODO: Refactor to CHAIN OF RESPONSIBILITY and ServiceLoader
        return Stream.of(
                MetricWeight.deserialize(entries.stream()),
                ImperialWeight.deserialize(entries.stream()),
                QueteletIndex.deserialize(entries.stream()),
                Combined.fromSerialized(entries.stream()),
                WholeBloodGlucose.deserialize(entries.stream()),
                PeripheralOxygenSaturation.fromSerialized(entries.stream()),
                BioelectricalImpedance.deserialize(entries.stream()),
                ImperialTemperature.deserialize(entries.stream()),
                MetricTemperature.deserialize(entries.stream()),
                software.visionary.vitalizr.bodyWater.BioelectricalImpedance.deserialize(entries.stream()),
                HeartrateMonitor.deserialize(entries.stream())
                ).flatMap(s -> s)
                .collect(Collectors.toList());
    }

    @Override
    public void serialize(final Collection<Vital> toWrite, final File data) {
        toWrite.forEach(v -> writeTo(data, v));
    }

    private static void writeTo(final File data, final Vital v) {
        // TODO: Refactor to CHAIN OF RESPONSIBILITY and ServiceLoader
        if (v instanceof MetricWeight) {
            new WriteObjectAsGZip<>(((MetricWeight) v).asSerializationProxy(), data.toPath()).run();
        } else if (v instanceof ImperialWeight) {
            new WriteObjectAsGZip<>(((ImperialWeight) v).asSerializationProxy(), data.toPath()).run();
        } else if (v instanceof QueteletIndex) {
            new WriteObjectAsGZip<>(((QueteletIndex) v).asSerializationProxy(), data.toPath()).run();
        } else if (v instanceof Combined) {
            new WriteObjectAsGZip<>(((Combined) v).toSerializationProxy(), data.toPath()).run();
        } else if (v instanceof WholeBloodGlucose) {
            new WriteObjectAsGZip<>(((WholeBloodGlucose) v).asSerializationProxy(), data.toPath()).run();
        } else if (v instanceof PeripheralOxygenSaturation) {
            new WriteObjectAsGZip<>(((PeripheralOxygenSaturation) v).asSerializationProxy(), data.toPath()).run();
        } else if (v instanceof BioelectricalImpedance) {
            new WriteObjectAsGZip<>(((BioelectricalImpedance) v).asSerializationProxy(), data.toPath()).run();
        } else if (v instanceof ImperialTemperature) {
            new WriteObjectAsGZip<>(((ImperialTemperature) v).asSerializationProxy(), data.toPath()).run();
        } else if (v instanceof MetricTemperature) {
            new WriteObjectAsGZip<>(((MetricTemperature) v).asSerializationProxy(), data.toPath()).run();
        } else if (v instanceof software.visionary.vitalizr.bodyWater.BioelectricalImpedance) {
            new WriteObjectAsGZip<>(((software.visionary.vitalizr.bodyWater.BioelectricalImpedance) v).asSerializationProxy(), data.toPath()).run();
        } else if (v instanceof HeartrateMonitor) {
            new WriteObjectAsGZip<>(((HeartrateMonitor) v).asSerializationProxy(), data.toPath()).run();
        }
    }
}
