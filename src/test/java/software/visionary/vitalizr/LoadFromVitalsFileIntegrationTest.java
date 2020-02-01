package software.visionary.vitalizr;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodPressure.Combined;
import software.visionary.vitalizr.bloodSugar.BloodSugar;
import software.visionary.vitalizr.bloodSugar.WholeBloodGlucose;
import software.visionary.vitalizr.serialization.WriteObjectAsGZip;
import software.visionary.vitalizr.weight.Weight;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoadFromVitalsFileIntegrationTest {
    @Test
    void canLoadVitalsFromFile() throws IOException {
        // Given: A file containing some vitals for a person
        final Person mom = Human.createPerson(UUID.randomUUID().toString() + ":Barbara Hidalgo-Toledo:1959-01-01:mom@mommy.net");
        final Combined toStore3 = Combined.systolicAndDiastolicBloodPressure(Instant.now().plus(-2, ChronoUnit.DAYS), 138, 89, mom);
        final BloodSugar toStore4 = new WholeBloodGlucose(Instant.now().plus(-2, ChronoUnit.DAYS), 225, mom);
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_load_vitals")).toFile();
        data.deleteOnExit();
        final Object serialized3 = toStore3.toSerializationProxy();
        final Object serialized4 = ((WholeBloodGlucose) toStore4).asSerializationProxy();
        final WriteObjectAsGZip<Object> writer3 = new WriteObjectAsGZip<>(serialized3, data.toPath());
        writer3.run();
        final WriteObjectAsGZip<Object> writer4 = new WriteObjectAsGZip<>(serialized4, data.toPath());
        writer4.run();
        // When: I call loadVitalsFromFile
        Vitalizr.loadVitalsFromFile(data);
        // And: I query for vitals I know are in that file
        final Collection<Weight> stored = Vitalizr.getWeightsFor(mom);
        // Then: The vitals should be returned
        final Collection<BloodPressure> storedAsWell = Vitalizr.getBloodPressuresFor(mom);
        assertTrue(storedAsWell.contains(toStore3));
        final Collection<BloodSugar> storedToo = Vitalizr.getBloodSugarsFor(mom);
        assertTrue(storedToo.contains(toStore4));
        data.delete();
    }
}
