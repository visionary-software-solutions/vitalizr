package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.AddVital;
import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Scanner;

public final class AddWeight extends AddVital<Weight> {
    @Override
    protected void saveVital(final Weight vital) {
        Vitalizr.storeWeight(vital);
    }

    @Override
    protected Weight deserialize(final Scanner scanner) {
        final String[] tokens = scanner.useDelimiter("\u0004").next().split("&");
        final Person person = Human.createPerson(tokens[0]);
        return getWeight(tokens[1], tokens[2], person);
    }

    private Weight getWeight(final String quantity, final String unit, final Person person) {
        return Kilogram.INSTANCE.getSymbol().equalsIgnoreCase(unit) ?
                new MetricWeight(Instant.now(), Double.parseDouble(quantity), person)
                : new ImperialWeight(Instant.now(), Double.parseDouble(quantity), person);
    }
}
