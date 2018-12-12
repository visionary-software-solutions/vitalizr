package software.visionary.vitalizr;

import org.threeten.extra.Interval;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.VitalRepository;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodPressure.InMemoryPersonBloodPressureRepository;
import software.visionary.vitalizr.bloodPressure.PersonBloodPressure;
import software.visionary.vitalizr.weight.InMemoryPersonWeightRepository;
import software.visionary.vitalizr.weight.PersonWeight;
import software.visionary.vitalizr.weight.Weight;

import java.util.ArrayList;
import java.util.Collection;

public final class Vitalizr {
    private static final VitalRepository<PersonWeight> REPOSITORY = new InMemoryPersonWeightRepository();
    private static final VitalRepository<PersonBloodPressure> PRESSURES = new InMemoryPersonBloodPressureRepository();
    private Vitalizr() {}

    public static void storeWeightFor(Person mom, Weight toStore) {
        REPOSITORY.save(new PersonWeight(mom, toStore));
    }

    public static Collection<PersonWeight> getWeightsFor(Person toFind) {
        final Collection<PersonWeight> found = new ArrayList<>();
        REPOSITORY.accept(pw -> {
            if (pw.getPerson().equals(toFind)) {
                found.add(pw);
            }
        });
        return found;
    }

    public static Collection<Person> listPeople() {
        final Collection<Person> found = new ArrayList<>();
        REPOSITORY.accept( pw -> found.add(pw.getPerson()));
        return found;
    }

    public static Collection<PersonWeight> getWeightsInInterval(final Person person, final Interval interval) {
        final Collection<PersonWeight> found = new ArrayList<>();
        REPOSITORY.accept(pw -> {
            if (pw.getPerson().equals(person) && interval.contains(pw.observedAt())) {
                found.add(pw);
            }
        });
        return found;
    }

    public static void storeBloodPressureFor(Person person, BloodPressure toStore) {
        PRESSURES.save(new PersonBloodPressure(person, toStore));
    }

    public static Collection<PersonBloodPressure> getBloodPressuresFor(Person person) {
        final Collection<PersonBloodPressure> found = new ArrayList<>();
        PRESSURES.accept(pb -> {
            if (pb.getPerson().equals(person)) {
                found.add(pb);
            }
        });
        return found;
    }
}
