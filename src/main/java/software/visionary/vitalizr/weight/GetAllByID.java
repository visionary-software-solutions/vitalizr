package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.ListVitals;
import software.visionary.vitalizr.api.Vital;

import java.util.Collection;
import java.util.Scanner;
import java.util.UUID;

public abstract class GetAllByID extends ListVitals {
    @Override
    protected Collection<? extends Vital> getVitals(final Scanner scanner) {
        final String input = scanner.useDelimiter("\u0004").next();
        return forId(UUID.fromString(input));
    }

    protected abstract Collection<? extends Vital> forId(final UUID id);
}
