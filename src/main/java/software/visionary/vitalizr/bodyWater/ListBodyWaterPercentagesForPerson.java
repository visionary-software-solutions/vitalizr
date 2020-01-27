package software.visionary.vitalizr.bodyWater;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.ListVitals;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.util.Collection;
import java.util.Scanner;

public final class ListBodyWaterPercentagesForPerson extends ListVitals {
    @Override
    protected Collection<BodyWaterPercentage> getVitals(final Scanner scanner) {
        final String input = scanner.useDelimiter("\u0004").next();
        final Person person = Human.createPerson(input);
        return Vitalizr.getBodyWaterPercentagesFor(person);
    }
}
