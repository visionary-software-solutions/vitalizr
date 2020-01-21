package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;

import java.util.Collection;
import java.util.Scanner;

public final class ListWeightsForPerson extends ListVitals {
    @Override
    protected Collection<? extends Vital> getVitals(final Scanner scanner) {
        final String input = scanner.next();
        final Person person = Human.createPerson(input);
        return Vitalizr.getWeightsFor(person);
    }
}
