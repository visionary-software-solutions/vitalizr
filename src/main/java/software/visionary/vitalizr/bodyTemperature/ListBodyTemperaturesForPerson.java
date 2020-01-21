package software.visionary.vitalizr.bodyTemperature;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.ListVitals;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;

import java.util.Collection;
import java.util.Scanner;

public final class ListBodyTemperaturesForPerson extends ListVitals {
    @Override
    protected Collection<? extends Vital> getVitals(final Scanner scanner) {
        final String input = scanner.next();
        final Person person = Human.createPerson(input);
        return Vitalizr.getBodyTemperaturesFor(person);
    }
}
