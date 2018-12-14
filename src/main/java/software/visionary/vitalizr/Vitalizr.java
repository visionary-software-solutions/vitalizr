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
    private static final VitalRepository<PersonWeight> WEIGHTS = new InMemoryPersonWeightRepository();
    private static final VitalRepository<PersonBloodPressure> PRESSURES = new InMemoryPersonBloodPressureRepository();

    private Vitalizr() {
    }

    public static void storeWeightFor(final Person mom, final Weight toStore) {
        WEIGHTS.save(new PersonWeight(mom, toStore));
    }

    public static Collection<PersonWeight> getWeightsFor(final Person toFind) {
        final Collection<PersonWeight> found = new ArrayList<>();
        WEIGHTS.accept(pw -> {
            if (pw.getPerson().equals(toFind)) {
                found.add(pw);
            }
        });
        return found;
    }

    static Collection<Person> listPeople() {
        final Collection<Person> found = new ArrayList<>();
        WEIGHTS.accept(pw -> found.add(pw.getPerson()));
        return found;
    }

    public static Collection<PersonWeight> getWeightsInInterval(final Person person, final Interval interval) {
        final Collection<PersonWeight> found = new ArrayList<>();
        WEIGHTS.accept(pw -> {
            if (pw.getPerson().equals(person) && interval.contains(pw.observedAt())) {
                found.add(pw);
            }
        });
        return found;
    }

    public static void storeBloodPressureFor(final Person person, final BloodPressure toStore) {
        PRESSURES.save(new PersonBloodPressure(person, toStore));
    }

    public static Collection<PersonBloodPressure> getBloodPressuresFor(final Person person) {
        final Collection<PersonBloodPressure> found = new ArrayList<>();
        PRESSURES.accept(pb -> {
            if (pb.getPerson().equals(person)) {
                found.add(pb);
            }
        });
        return found;
    }

    public static Collection<PersonBloodPressure> getBloodPressuresInInterval(final Person person, final Interval interval) {
        final Collection<PersonBloodPressure> found = new ArrayList<>();
        PRESSURES.accept(pw -> {
            if (pw.getPerson().equals(person) && interval.contains(pw.observedAt())) {
                found.add(pw);
            }
        });
        return found;
    }
}
