package software.visionary.vitalizr;

import org.threeten.extra.Interval;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;
import software.visionary.vitalizr.api.VitalRepository;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodSugar.BloodSugar;
import software.visionary.vitalizr.oxygen.BloodOxygen;
import software.visionary.vitalizr.pulse.Pulse;
import software.visionary.vitalizr.weight.Weight;

import java.util.ArrayList;
import java.util.Collection;

public final class Vitalizr {
    private static final VitalRepository<Vital> VITALS = new InMemoryVitalRepository();

    private Vitalizr() {
    }

    public static void storeWeightFor(final Weight toStore) {
        VITALS.save(toStore);
    }

    public static Collection<Weight> getWeightsFor(final Person toFind) {
        final Collection<Weight> found = new ArrayList<>();
        VITALS.accept(vital -> {
            if (vital.belongsTo().equals(toFind) && vital instanceof Weight) {
                found.add((Weight) vital);
            }
        });
        return found;
    }

    static Collection<Lifeform> listPeople() {
        final Collection<Lifeform> found = new ArrayList<>();
        VITALS.accept(vital -> found.add(vital.belongsTo()));
        return found;
    }

    public static Collection<Weight> getWeightsInInterval(final Person person, final Interval interval) {
        final Collection<Weight> found = new ArrayList<>();
        VITALS.accept(vital -> {
            if (vital.belongsTo().equals(person) && interval.contains(vital.observedAt()) && vital instanceof Weight) {
                found.add((Weight) vital);
            }
        });
        return found;
    }

    public static void storeBloodPressureFor(final BloodPressure toStore) {
        VITALS.save(toStore);
    }

    public static Collection<BloodPressure> getBloodPressuresFor(final Person person) {
        final Collection<BloodPressure> found = new ArrayList<>();
        VITALS.accept(vital -> {
            if (vital.belongsTo().equals(person) && vital instanceof BloodPressure) {
                found.add(((BloodPressure) vital));
            }
        });
        return found;
    }

    public static Collection<BloodPressure> getBloodPressuresInInterval(final Person person, final Interval interval) {
        final Collection<BloodPressure> found = new ArrayList<>();
        VITALS.accept(vital -> {
            if (vital.belongsTo().equals(person) && interval.contains(vital.observedAt()) && vital instanceof BloodPressure) {
                found.add(((BloodPressure) vital));
            }
        });
        return found;
    }

    public static void storePulseFor(final Pulse pulse) {
        VITALS.save(pulse);
    }

    public static Collection<Pulse> getPulsesFor(final Person person) {
        final Collection<Pulse> found = new ArrayList<>();
        VITALS.accept(vital -> {
            if (vital.belongsTo().equals(person) && vital instanceof Pulse) {
                found.add((Pulse) vital);
            }
        });
        return found;
    }

    public static Collection<Pulse> getPulsesInInterval(final Person person, final Interval interval) {
        final Collection<Pulse> found = new ArrayList<>();
        VITALS.accept(vital -> {
            if (vital.belongsTo().equals(person) && interval.contains(vital.observedAt()) && vital instanceof Pulse) {
                found.add((Pulse) vital);
            }
        });
        return found;
    }

    public static void storeBloodOxygenFor(final BloodOxygen oxygen) {
        VITALS.save(oxygen);
    }

    public static Collection<BloodOxygen> getBloodOxygensFor(final Person person) {
        final Collection<BloodOxygen> found = new ArrayList<>();
        VITALS.accept(vital -> {
            if (vital.belongsTo().equals(person) && vital instanceof BloodOxygen) {
                found.add((BloodOxygen) vital);
            }
        });
        return found;
    }

    public static Collection<BloodOxygen> getBloodOxygensInInterval(final Person person, final Interval interval) {
        final Collection<BloodOxygen> found = new ArrayList<>();
        VITALS.accept(vital -> {
            if (vital.belongsTo().equals(person) && interval.contains(vital.observedAt()) && vital instanceof BloodOxygen) {
                found.add((BloodOxygen) vital);
            }
        });
        return found;
    }

    public static void storeBloodSugarFor(final BloodSugar toStore) {
        VITALS.save(toStore);
    }

    public static Collection<BloodSugar> getBloodSugarsFor(final Person person) {
        final Collection<BloodSugar> found = new ArrayList<>();
        VITALS.accept(vital -> {
            if (vital.belongsTo().equals(person) && vital instanceof BloodSugar) {
                found.add((BloodSugar) vital);
            }
        });
        return found;
    }

    public static Collection<BloodSugar> getBloodSugarsInInterval(final Person person, final Interval interval) {
        final Collection<BloodSugar> found = new ArrayList<>();
        VITALS.accept(vital -> {
            if (vital.belongsTo().equals(person) && interval.contains(vital.observedAt()) && vital instanceof BloodSugar) {
                found.add((BloodSugar) vital);
            }
        });
        return found;
    }
}
