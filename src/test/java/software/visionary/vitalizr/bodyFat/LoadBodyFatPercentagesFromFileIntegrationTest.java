package software.visionary.vitalizr.bodyFat;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
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

class LoadBodyFatPercentagesFromFileIntegrationTest {
    @Test
    void canLoadBodyFatPercentagesFromFile() throws IOException {
        // Given: A file containing some vitals for a person
        final Person mom = Fixtures.createRandomPerson();
        final BodyFatPercentage toStore2 = new BioelectricalImpedance(Instant.now().plus(-2, ChronoUnit.DAYS), 33.1, mom);
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_load_vitals")).toFile();
        data.deleteOnExit();
        final Object serialized2 = ((BioelectricalImpedance) toStore2).asSerializationProxy();
        final WriteObjectAsGZip<Object> writer2 = new WriteObjectAsGZip<>(serialized2, data.toPath());
        writer2.run();
        // When: I call loadVitalsFromFile
        Vitalizr.loadVitalsFromFile(data);
        // And: I query for vitals I know are in that file
        final Collection<BodyFatPercentage> stored = Vitalizr.getBodyFatPercentagesFor(mom);
        // Then: The vitals should be returned
        assertTrue(stored.contains(toStore2));
        data.delete();
    }
}
