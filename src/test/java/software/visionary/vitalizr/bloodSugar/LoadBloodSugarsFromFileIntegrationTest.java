package software.visionary.vitalizr.bloodSugar;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.VitalPersister;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.serialization.WriteObjectAsGZip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoadBloodSugarsFromFileIntegrationTest {
    @Test
    void canLoadBloodSugarsFromFile() throws IOException {
        // Given: A file containing some vitals for a person
        final Person mom = Fixtures.createRandomPerson();
        final BloodSugar toStore4 = new WholeBloodGlucose(Instant.now().plus(-2, ChronoUnit.DAYS), 225, mom);
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_load_vitals")).toFile();
        data.deleteOnExit();
        final Object serialized = ((WholeBloodGlucose) toStore4).asSerializationProxy();
        final WriteObjectAsGZip<Object> writer4 = new WriteObjectAsGZip<>(serialized, data.toPath());
        writer4.run();
        // When: I call loadVitalsFromFile
        VitalPersister.loadVitalsFromFile(data);
        // And: I query for vitals I know are in that file
        // Then: The vitals should be returned
        final Collection<BloodSugar> stored = Vitalizr.getBloodSugarsFor(mom);
        assertTrue(stored.contains(toStore4));
        data.delete();
    }
}
