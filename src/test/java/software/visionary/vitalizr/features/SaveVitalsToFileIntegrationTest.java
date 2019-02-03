package software.visionary.vitalizr.features;

import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bodyMassIndex.BodyMassIndex;
import software.visionary.vitalizr.bodyMassIndex.BodyMassIndexSerializationProxy;
import software.visionary.vitalizr.bodyMassIndex.QueteletIndex;
import software.visionary.vitalizr.serialization.GZipFiles;
import software.visionary.vitalizr.weight.MetricWeight;
import software.visionary.vitalizr.weight.MetricWeightSerializationProxy;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SaveVitalsToFileIntegrationTest {
    @Test
    void canSaveVitalsToFile() throws IOException {
        // Given: A person
        final Person mom = Human.createPerson("Barbara Hidalgo-Toledo:1959-01-01:mom@mommy.net");
        // And: Some Vitals for the person
        final Collection<MetricWeight> stored = new ArrayList<>(3);
        stored.add(MetricWeight.inKilograms(100, Instant.now(), mom));
        stored.add(MetricWeight.inKilograms(101, Instant.now().plus(-1, ChronoUnit.DAYS), mom));
        stored.add(MetricWeight.inKilograms(105, Instant.now().plus(-2, ChronoUnit.DAYS), mom));
        final Collection<BodyMassIndex> alsoStored = new ArrayList<>(3);
        alsoStored.add(new QueteletIndex(Instant.now(), 39.9, mom));
        alsoStored.add(new QueteletIndex(Instant.now().plus(-1, ChronoUnit.DAYS), 40.1, mom));
        alsoStored.add(new QueteletIndex(Instant.now().plus(-2, ChronoUnit.DAYS), 40.2, mom));
        // And: Vitalizr has stored those vitals
        stored.forEach(Vitalizr::storeWeightFor);
        alsoStored.forEach(Vitalizr::storeBodyMassIndexFor);
        // And: A File to write the data to
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), mom.getEmailAddress().toString() + "_vitals")).toFile();
        data.deleteOnExit();
        // When: I call saveVitalsToFile
        Vitalizr.saveVitalsToFile(data);
        // Then: The vitals should be stored in the file
        final List<String> written = GZipFiles.slurpGZippedFile(data.toPath(), StandardCharsets.UTF_8);
        final List<MetricWeight> foundWeights = MetricWeightSerializationProxy.stream(written).collect(Collectors.toList());
        assertTrue(foundWeights.containsAll(stored));
        final List<BodyMassIndex> foundBMIs = BodyMassIndexSerializationProxy.stream(written).collect(Collectors.toList());
        assertTrue(foundBMIs.containsAll(alsoStored));
        data.delete();
    }
}
