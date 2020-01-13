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
        try {
            Vitalizr.loadAll();
        } catch (IOException e) {
            writeToOutput(e.getMessage(), sent);
            closeConnection(sent);
            return;
        }
        final String input = getInput(received);
        final Person person = Human.createPerson(input);
        final Collection<Weight> weights = Vitalizr.getWeightsFor(person);
        writeToOutput(weights.stream().map(Object::toString).collect(Collectors.joining(",")), sent);
        closeConnection(sent);
    }

    private void writeToOutput(final String message, final OutputStream sent) {
        try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sent))){
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeConnection(final OutputStream sent) {
        try {
            sent.close();
        } catch (IOException e) {
            writeToOutput(e.getMessage(), sent);
        }
    }

    private static String getInput(final InputStream received) {
        String text;
        try (Scanner scanner = new Scanner(received, StandardCharsets.UTF_8.name())) {
            text = scanner.next();
        }
        return text;
    }
}
