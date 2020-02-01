package software.visionary.vitalizr;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodSugar.WholeBloodGlucose;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

class ListPeopleTest {
    @Test
    @Disabled("affected by other tests in suite, works when run in isolation")
    void startsEmpty() throws IOException {
        final StringWriter buffer = new StringWriter();
        final BufferedWriter writer = new BufferedWriter(buffer);
        final ListPeople toTest = new ListPeople();
        toTest.tryDo(writer);
        writer.flush();
        Assertions.assertEquals("No people stored\n", buffer.toString());
    }

    @Test
    void returnsPeopleIfStored() throws IOException {
        final Person p = Fixtures.createRandomPerson();
        final File data = Files.createFile(Paths.get(System.getProperty("user.dir"), p.getEmailAddress().toString() + "_save_vitals")).toFile();
        data.deleteOnExit();
        Vitalizr.storeBloodSugar(new WholeBloodGlucose(Instant.now(), 240, p));
        Vitalizr.saveVitalsToFile(data);
        final StringWriter buffer = new StringWriter();
        final BufferedWriter writer = new BufferedWriter(buffer);
        final ListPeople toTest = new ListPeople();
        toTest.tryDo(writer);
        writer.flush();
        Assertions.assertTrue(buffer.toString().contains(p.toString()));
        data.delete();
    }
}
