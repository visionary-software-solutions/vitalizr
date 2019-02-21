package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodPressure.Combined;
import software.visionary.vitalizr.bloodPressure.CombinedBloodPressureConverter;
import software.visionary.vitalizr.bloodPressure.CombinedBloodPressureSerializationProxy;
import software.visionary.vitalizr.bloodSugar.BloodSugar;
import software.visionary.vitalizr.bloodSugar.WholeBloodGlucoseSerializationProxy;
import software.visionary.vitalizr.bloodSugar.WholeBloodGlucose;
import software.visionary.vitalizr.bloodSugar.WholeBloodGlucoseConverter;
import software.visionary.vitalizr.bodyMassIndex.BodyMassIndex;
import software.visionary.vitalizr.bodyMassIndex.QuetletIndexSerializationProxy;
import software.visionary.vitalizr.bodyMassIndex.QueteletIndex;
import software.visionary.vitalizr.bodyMassIndex.QuetletIndexConverter;
import software.visionary.vitalizr.serialization.WriteObjectAsGZip;
import software.visionary.vitalizr.weight.MetricWeight;
import software.visionary.vitalizr.weight.MetricWeightConverter;
import software.visionary.vitalizr.weight.MetricWeightSerializationProxy;
import software.visionary.vitalizr.weight.Weight;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoadFromVitalsFileIntegrationTest {
    @Test
    void canLoadVitalsFromFile() throws IOException {
        // Given: A file containing some vitals for a person
        final Person mom = Human.createPerson("Barbara Hidalgo-Toledo:1959-01-01:mom@mommy.net");
        final MetricWeight toStore = MetricWeight.inKilograms(100, Instant.now(), mom);
        final BodyMassIndex toStore2 = new QueteletIndex(Instant.now().plus(-2, ChronoUnit.DAYS), 33.1, mom);
        final Combined toStore3 = Combined.systolicAndDiastolicBloodPressure(Instant.now().plus(-2, ChronoUnit.DAYS), 138, 89, mom);
        final BloodSugar toStore4 = new WholeBloodGlucose(Instant.now().plus(-2, ChronoUnit.DAYS), 225, mom);
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_load_vitals")).toFile();
        data.deleteOnExit();
        final MetricWeightSerializationProxy serialized = MetricWeightConverter.INSTANCE.to(toStore);
        final QuetletIndexSerializationProxy serialized2 = QuetletIndexConverter.INSTANCE.to((QueteletIndex) toStore2);
        final CombinedBloodPressureSerializationProxy serialized3 = CombinedBloodPressureConverter.INSTANCE.to(toStore3);
        final WholeBloodGlucoseSerializationProxy serialized4 = WholeBloodGlucoseConverter.INSTANCE.to((WholeBloodGlucose) toStore4);
        final WriteObjectAsGZip<MetricWeightSerializationProxy> writer = new WriteObjectAsGZip<>(serialized, data.toPath());
        writer.run();
        final WriteObjectAsGZip<QuetletIndexSerializationProxy> writer2 = new WriteObjectAsGZip<>(serialized2, data.toPath());
        writer2.run();
        final WriteObjectAsGZip<CombinedBloodPressureSerializationProxy> writer3 = new WriteObjectAsGZip<>(serialized3, data.toPath());
        writer3.run();
        final WriteObjectAsGZip<WholeBloodGlucoseSerializationProxy> writer4 = new WriteObjectAsGZip<>(serialized4, data.toPath());
        writer4.run();
        // When: I call loadVitalsFromFile
        Vitalizr.loadVitalsFromFile(data);
        // And: I query for vitals I know are in that file
        final Collection<Weight> stored = Vitalizr.getWeightsFor(mom);
        // Then: The vitals should be returned
        assertTrue(stored.contains(toStore));
        final Collection<BodyMassIndex> alsoStored = Vitalizr.getBodyMassIndicesFor(mom);
        assertTrue(alsoStored.contains(toStore2));
        final Collection<BloodPressure> storedAsWell = Vitalizr.getBloodPressuresFor(mom);
        assertTrue(storedAsWell.contains(toStore3));
        final Collection<BloodSugar> storedToo = Vitalizr.getBloodSugarsFor(mom);
        assertTrue(storedToo.contains(toStore4));
        data.delete();
    }
}
