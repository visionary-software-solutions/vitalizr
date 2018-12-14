package software.visionary.vitalizr;

import org.threeten.extra.Interval;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.VitalRepository;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodPressure.InMemoryPersonBloodPressureRepository;
import software.visionary.vitalizr.bloodPressure.PersonBloodPressure;
import software.visionary.vitalizr.bloodSugar.BloodSugar;
import software.visionary.vitalizr.bloodSugar.InMemoryPersonBloodSugarRepository;
import software.visionary.vitalizr.bloodSugar.PersonBloodSugar;
import software.visionary.vitalizr.oxygen.BloodOxygen;
import software.visionary.vitalizr.oxygen.InMemoryPersonBloodOxygenRepository;
import software.visionary.vitalizr.oxygen.PersonBloodOxygen;
import software.visionary.vitalizr.pulse.InMemoryPersonPulseRepository;
import software.visionary.vitalizr.pulse.PersonPulse;
import software.visionary.vitalizr.pulse.Pulse;
import software.visionary.vitalizr.weight.InMemoryPersonWeightRepository;
import software.visionary.vitalizr.weight.PersonWeight;
import software.visionary.vitalizr.weight.Weight;

import java.util.ArrayList;
import java.util.Collection;

public final class Vitalizr {
    private static final VitalRepository<PersonWeight> WEIGHTS = new InMemoryPersonWeightRepository();
    private static final VitalRepository<PersonBloodPressure> PRESSURES = new InMemoryPersonBloodPressureRepository();
    private static final VitalRepository<PersonPulse> PULSES = new InMemoryPersonPulseRepository();
    private static final VitalRepository<PersonBloodOxygen> OXYGENS = new InMemoryPersonBloodOxygenRepository();
    private static final VitalRepository<PersonBloodSugar> SUGARS = new InMemoryPersonBloodSugarRepository();

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

    public static void storePulseFor(final Person person, final Pulse pulse) {
        PULSES.save(new PersonPulse(person, pulse));
    }

    public static Collection<PersonPulse> getPulsesFor(final Person person) {
        final Collection<PersonPulse> found = new ArrayList<>();
        PULSES.accept(pb -> {
            if (pb.getPerson().equals(person)) {
                found.add(pb);
            }
        });
        return found;
    }

    public static Collection<PersonPulse> getPulsesInInterval(final Person person, final Interval interval) {
        final Collection<PersonPulse> found = new ArrayList<>();
        PULSES.accept(pw -> {
            if (pw.getPerson().equals(person) && interval.contains(pw.observedAt())) {
                found.add(pw);
            }
        });
        return found;
    }

    public static void storeBloodOxygenFor(final Person person, final BloodOxygen oxygen) {
        OXYGENS.save(new PersonBloodOxygen(person, oxygen));
    }

    public static Collection<PersonBloodOxygen> getBloodOxygensFor(final Person person) {
        final Collection<PersonBloodOxygen> found = new ArrayList<>();
        OXYGENS.accept(pb -> {
            if (pb.getPerson().equals(person)) {
                found.add(pb);
            }
        });
        return found;
    }

    public static Collection<PersonBloodOxygen> getBloodOxygensInInterval(final Person person, final Interval interval) {
        final Collection<PersonBloodOxygen> found = new ArrayList<>();
        OXYGENS.accept(pw -> {
            if (pw.getPerson().equals(person) && interval.contains(pw.observedAt())) {
                found.add(pw);
            }
        });
        return found;
    }

    public static void storeBloodSugarFor(final Person person, final BloodSugar toStore) {
        SUGARS.save(new PersonBloodSugar(person, toStore));
    }

    public static Collection<PersonBloodSugar> getBloodSugarsFor(final Person person) {
        final Collection<PersonBloodSugar> found = new ArrayList<>();
        SUGARS.accept(pb -> {
            if (pb.getPerson().equals(person)) {
                found.add(pb);
            }
        });
        return found;
    }
}
