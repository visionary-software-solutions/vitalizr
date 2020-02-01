package software.visionary.vitalizr.bodyTemperature;

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

class LoadBodyTemperaturesFromFileIntegrationTest {

    public static final Person PERSON = Fixtures.createRandomPerson();

    @Test
    void canLoadImperialBodyTemperaturesFromFile() throws IOException {
        // Given: A file containing some vitals for a person
        final BodyTemperature toStore2 = new ImperialTemperature(Instant.now().plus(-2, ChronoUnit.DAYS), 98.7, PERSON);
        final Object serialized2 = ((ImperialTemperature) toStore2).asSerializationProxy();
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), PERSON.getEmailAddress().toString() + "_load_vitals")).toFile();
        data.deleteOnExit();
        final WriteObjectAsGZip<Object> writer2 = new WriteObjectAsGZip<>(serialized2, data.toPath());
        writer2.run();
        // When: I call loadVitalsFromFile
        Vitalizr.loadVitalsFromFile(data);
        // And: I query for vitals I know are in that file
        final Collection<BodyTemperature> stored = Vitalizr.getBodyTemperaturesFor(PERSON);
        // Then: The vitals should be returned
        assertTrue(stored.contains(toStore2));
        data.delete();
    }

    @Test
    void canMetricImperialBodyTemperaturesFromFile() throws IOException {
        // Given: A file containing some vitals for a person
        final BodyTemperature toStore2 = new MetricTemperature(Instant.now().plus(-2, ChronoUnit.DAYS), 36.1, PERSON);
        final Object serialized2 = ((MetricTemperature) toStore2).asSerializationProxy();
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), PERSON.getEmailAddress().toString() + "_load_vitals")).toFile();
        data.deleteOnExit();
        final WriteObjectAsGZip<Object> writer2 = new WriteObjectAsGZip<>(serialized2, data.toPath());
        writer2.run();
        // When: I call loadVitalsFromFile
        Vitalizr.loadVitalsFromFile(data);
        // And: I query for vitals I know are in that file
        final Collection<BodyTemperature> stored = Vitalizr.getBodyTemperaturesFor(PERSON);
        // Then: The vitals should be returned
        assertTrue(stored.contains(toStore2));
        data.delete();
    }
}
