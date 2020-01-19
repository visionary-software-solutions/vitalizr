package software.visionary.vitalizr.weight;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import software.visionary.vitalizr.Fixtures;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.serialization.WriteObjectAsGZip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoadWeightsFromFileIntegrationTest {

    public static final Person PERSON = Fixtures.createRandomPerson();

    @ParameterizedTest
    @MethodSource
    void canLoadWeightsFromFile(final Weight toStore, final Object serialized) throws IOException {
        // Given: A file containing some vitals for a person
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), PERSON.getEmailAddress().toString() + "_load_vitals")).toFile();
        data.deleteOnExit();
        final WriteObjectAsGZip<Object> writer = new WriteObjectAsGZip<>(serialized, data.toPath());
        writer.run();
        // When: I call loadVitalsFromFile
        Vitalizr.loadVitalsFromFile(data);
        // And: I query for vitals I know are in that file
        final Collection<Weight> stored = Vitalizr.getWeightsFor(PERSON);
        // Then: The vitals should be returned
        assertTrue(stored.contains(toStore));
        data.delete();
    }

    private static Stream<Arguments> canLoadWeightsFromFile() {
        final ImperialWeight imperialWeight = new ImperialWeight(Instant.now(), 203.8, PERSON);
        final MetricWeight metricWeight = MetricWeight.inKilograms(100, Instant.now(), PERSON);
        return Stream.of(
                Arguments.of(imperialWeight, imperialWeight.asSerializationProxy()),
                Arguments.of(metricWeight, metricWeight.asSerializationProxy())
        );
    }
}
