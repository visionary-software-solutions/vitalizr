package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.AddVitalToPerson;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;

import java.time.Instant;
import java.util.Scanner;

public final class AddWeightToPerson extends AddVitalToPerson {
    @Override
    protected Vital saveVital(final Scanner scanner) {
        final String[] tokens = scanner.useDelimiter("\u0004").next().split("&");
        final Person person = Human.createPerson(tokens[0]);
        final Weight store = getWeight(tokens[1], tokens[2], person);
        Vitalizr.storeWeightFor(store);
        return store;
    }

    private Weight getWeight(final String quantity, final String unit, final Person person) {
        return Gram.INSTANCE.getSymbol().equalsIgnoreCase(unit) ?
                new MetricWeight(Instant.now(), Double.parseDouble(quantity), person)
                : new ImperialWeight(Instant.now(), Double.parseDouble(quantity), person);
    }
}
