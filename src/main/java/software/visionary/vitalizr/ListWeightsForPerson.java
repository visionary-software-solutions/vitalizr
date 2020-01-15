package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.weight.Weight;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class ListWeightsForPerson implements BiConsumer<InputStream, OutputStream> {
    @Override
    public void accept(final InputStream received, final OutputStream sent) {
        try (final Scanner scanner = new Scanner(received, StandardCharsets.UTF_8.name());
             final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sent)))  {
            Vitalizr.loadAll();
            final String input = scanner.next();
            final Person person = Human.createPerson(input);
            final Collection<Weight> weights = Vitalizr.getWeightsFor(person);
            writer.write(weights.stream().map(Object::toString).collect(Collectors.joining(",")));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
