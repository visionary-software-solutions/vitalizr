package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.ListVitals;
import software.visionary.vitalizr.Vitalizr;

import java.util.Collection;
import java.util.Scanner;
import java.util.UUID;

public final class ListWeights extends ListVitals {
    @Override
    protected Collection<Weight> getVitals(final Scanner scanner) {
        final String input = scanner.useDelimiter("\u0004").next();
        return Vitalizr.getWeightsById(UUID.fromString(input));
    }
}
