package software.visionary.vitalizr.bloodPressure;

import software.visionary.vitalizr.Human;
import software.visionary.vitalizr.ListVitals;
import software.visionary.vitalizr.Vitalizr;
import software.visionary.vitalizr.api.Person;

import java.util.Collection;
import java.util.Scanner;

public final class ListBloodPressuresForPerson extends ListVitals {
    @Override
    protected Collection<BloodPressure> getVitals(final Scanner scanner) {
        final String input = scanner.useDelimiter("\u0004").next();
        final Person person = Human.createPerson(input);
        return Vitalizr.getBloodPressuresFor(person);
    }
}
