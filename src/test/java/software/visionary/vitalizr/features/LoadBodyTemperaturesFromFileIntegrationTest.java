package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bodyTemperature.*;
import software.visionary.vitalizr.serialization.WriteObjectAsGZip;

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
        final BodyTemperature toStore2 = new ImperialTemperature(PERSON, 98.7, Instant.now().plus(-2, ChronoUnit.DAYS));
        final ImperialTemperatureSerializationProxy serialized2 = ImperialTemperatureConverter.INSTANCE.to((ImperialTemperature) toStore2);
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), PERSON.getEmailAddress().toString() + "_load_vitals")).toFile();
        data.deleteOnExit();
        final WriteObjectAsGZip<ImperialTemperatureSerializationProxy> writer2 = new WriteObjectAsGZip<>(serialized2, data.toPath());
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
        final BodyTemperature toStore2 = new MetricTemperature(PERSON, 36.1, Instant.now().plus(-2, ChronoUnit.DAYS));
        final MetricTemperatureSerializationProxy serialized2 = MetricTemperatureConverter.INSTANCE.to((MetricTemperature) toStore2);
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), PERSON.getEmailAddress().toString() + "_load_vitals")).toFile();
        data.deleteOnExit();
        final WriteObjectAsGZip<MetricTemperatureSerializationProxy> writer2 = new WriteObjectAsGZip<>(serialized2, data.toPath());
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
