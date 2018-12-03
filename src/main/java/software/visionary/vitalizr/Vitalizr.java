package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.VitalRepository;
import software.visionary.vitalizr.weight.InMemoryPersonWeightRepository;
import software.visionary.vitalizr.weight.PersonWeight;
import software.visionary.vitalizr.weight.Weight;

import java.util.ArrayList;
import java.util.Collection;

public final class Vitalizr {
    private static final VitalRepository<PersonWeight> REPOSITORY = new InMemoryPersonWeightRepository();
    private Vitalizr() {}

    public static void storeWeightFor(Person mom, Weight toStore) {
        REPOSITORY.save(new PersonWeight(mom, toStore));
    }

    public static Collection<PersonWeight> getWeightsFor(Person toFind) {
        final Collection<PersonWeight> found = new ArrayList<>();
        REPOSITORY.accept(pw -> {
            if (pw.getPerson().equals(toFind)) {
                found.add(pw);
            }});
        return found;
    }

    public static Collection<Person> listPeople() {
        final Collection<Person> found = new ArrayList<>();
        REPOSITORY.accept( pw -> found.add(pw.getPerson()));
        return found;
    }
}
