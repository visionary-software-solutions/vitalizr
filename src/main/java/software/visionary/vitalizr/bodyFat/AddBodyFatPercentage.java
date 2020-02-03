package software.visionary.vitalizr.bodyFat;

import software.visionary.vitalizr.AddVital;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class AddBodyFatPercentage extends AddVital<BodyFatPercentage> {
    @Override
    protected void saveVital(final BodyFatPercentage store) {
        Vitalizr.storeBodyFatPercentage(store);
    }

    @Override
    protected BodyFatPercentage deserialize(final Scanner scanner) {
        final String input = scanner.useDelimiter("\u0004").tokens().collect(Collectors.joining());
        final String[] tokens = input.split("&");
        final Person person = lookupExistingOrCreateNew(tokens[0]);
        return new BioelectricalImpedance(Instant.now(), Double.parseDouble(tokens[1]), person);
    }
}
