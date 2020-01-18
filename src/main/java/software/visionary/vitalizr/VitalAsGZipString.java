package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Vital;
import software.visionary.vitalizr.api.VitalSerializationStrategy;
import software.visionary.vitalizr.bloodPressure.Combined;
import software.visionary.vitalizr.bloodPressure.CombinedBloodPressureConverter;
import software.visionary.vitalizr.bloodPressure.StringCombinedBloodPressureConverter;
import software.visionary.vitalizr.bloodSugar.StringWholeBloodGlucoseConverter;
import software.visionary.vitalizr.bloodSugar.WholeBloodGlucose;
import software.visionary.vitalizr.bloodSugar.WholeBloodGlucoseConverter;
import software.visionary.vitalizr.bodyFat.BioelectricalImpedance;
import software.visionary.vitalizr.bodyFat.BioelectricalImpedanceConverter;
import software.visionary.vitalizr.bodyFat.StringBioelectricalImpedanceConverter;
import software.visionary.vitalizr.bodyMassIndex.QueteletIndex;
import software.visionary.vitalizr.bodyMassIndex.QuetletIndexConverter;
import software.visionary.vitalizr.bodyMassIndex.StringQuetletIndexConverter;
import software.visionary.vitalizr.bodyTemperature.ImperialTemperature;
import software.visionary.vitalizr.bodyTemperature.ImperialTemperatureConverter;
import software.visionary.vitalizr.bodyTemperature.StringImperialTemperatureConverter;
import software.visionary.vitalizr.oxygen.PeripheralOxygenSaturation;
import software.visionary.vitalizr.oxygen.PeripheralOxygenSaturationConverter;
import software.visionary.vitalizr.oxygen.StringPeripheralOxygenSaturationConverter;
import software.visionary.vitalizr.serialization.GZipFiles;
import software.visionary.vitalizr.serialization.WriteObjectAsGZip;
import software.visionary.vitalizr.weight.MetricWeight;
import software.visionary.vitalizr.weight.MetricWeightConverter;
import software.visionary.vitalizr.weight.StringMetricWeightConverter;

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
                StringMetricWeightConverter.INSTANCE.to(entries.stream()),
                StringQuetletIndexConverter.INSTANCE.to(entries.stream()),
                StringCombinedBloodPressureConverter.INSTANCE.to(entries.stream()),
                StringWholeBloodGlucoseConverter.INSTANCE.to(entries.stream()),
                StringPeripheralOxygenSaturationConverter.INSTANCE.to(entries.stream()),
                StringBioelectricalImpedanceConverter.INSTANCE.to(entries.stream()),
                StringImperialTemperatureConverter.INSTANCE.to(entries.stream())
                ).flatMap(s -> s)
                .collect(Collectors.toList());
    }

    @Override
    public void serialize(final Collection<Vital> toWrite, final File data) {
        toWrite.forEach(v -> writeTo(data, v));
    }

    private static void writeTo(final File data, final Vital v) {
        // TODO: Add support for other Vitals
        if (v instanceof MetricWeight) {
            new WriteObjectAsGZip<>(MetricWeightConverter.INSTANCE.to((MetricWeight) v), data.toPath()).run();
        } else if (v instanceof QueteletIndex) {
            new WriteObjectAsGZip<>(QuetletIndexConverter.INSTANCE.to((QueteletIndex) v), data.toPath()).run();
        } else if (v instanceof Combined) {
            new WriteObjectAsGZip<>(CombinedBloodPressureConverter.INSTANCE.to((Combined) v), data.toPath()).run();
        } else if (v instanceof WholeBloodGlucose) {
            new WriteObjectAsGZip<>(WholeBloodGlucoseConverter.INSTANCE.to((WholeBloodGlucose) v), data.toPath()).run();
        } else if (v instanceof PeripheralOxygenSaturation) {
            new WriteObjectAsGZip<>(PeripheralOxygenSaturationConverter.INSTANCE.to((PeripheralOxygenSaturation) v), data.toPath()).run();
        } else if (v instanceof BioelectricalImpedance) {
            new WriteObjectAsGZip<>(BioelectricalImpedanceConverter.INSTANCE.to((BioelectricalImpedance) v), data.toPath()).run();
        } else if (v instanceof ImperialTemperature) {
            new WriteObjectAsGZip<>(ImperialTemperatureConverter.INSTANCE.to((ImperialTemperature) v), data.toPath()).run();
        }
    }
}
