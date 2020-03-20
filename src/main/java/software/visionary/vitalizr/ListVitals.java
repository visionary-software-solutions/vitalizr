package software.visionary.vitalizr;

import software.visionary.iluvatar.Wish;
import software.visionary.vitalizr.api.Vital;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Collectors;

public abstract class ListVitals extends Wish {
    @Override
    protected void doCommand(final Scanner scanner, final BufferedWriter writer) {
        try {
            final Collection<? extends Vital> vitals = getVitals(scanner);
            writer.write(serialize(vitals));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String serialize(final Collection<? extends Vital> vitals) {
        final String collect = vitals.stream().map(this::serialize).collect(Collectors.joining("\u0023"));
        return String.format("%s\u0004", collect);
    }

    private String serialize(final Vital vital) {
        return String.format("%d\u2049%.02f\u2049%s", vital.observedAt().toEpochMilli(), vital.getQuantity().doubleValue(), vital.getUnit().getSymbol());
    }

    protected abstract Collection<? extends Vital> getVitals(final Scanner scanner);
}
