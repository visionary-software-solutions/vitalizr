package software.visionary.vitalizr.bodyTemperature;

import software.visionary.vitalizr.AddVital;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.time.Instant;
import java.util.Scanner;

public final class AddBodyTemperature extends AddVital<BodyTemperature> {
    @Override
    protected void saveVital(final BodyTemperature store) {
        Vitalizr.storeTemperature(store);
    }

    @Override
    protected BodyTemperature deserialize(final Scanner scanner) {
        final String[] tokens = scanner.useDelimiter("\u0004").next().split("&");
        final Person person = lookupExistingOrCreateNew(tokens[0]);
        return Celsius.INSTANCE.getSymbol().equalsIgnoreCase(tokens[2]) ?
                new MetricTemperature(Instant.now(), Double.parseDouble(tokens[1]), person) :
                new ImperialTemperature(Instant.now(), Double.parseDouble(tokens[1]), person);
    }
}
