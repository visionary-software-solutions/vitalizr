package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Vital;

import java.io.BufferedWriter;
import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Collectors;

public abstract class ListVitals extends Wish {
    @Override
    protected void doCommand(final Scanner scanner, final BufferedWriter writer) {
        try {
            Vitalizr.loadAll();
            final Collection<? extends Vital> weights = getVitals(scanner);
            writer.write(weights.stream().map(Object::toString).collect(Collectors.joining(",")));
            writer.newLine();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Collection<? extends Vital> getVitals(final Scanner scanner);
}
