package software.visionary.vitalizr;

import org.threeten.extra.Interval;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Vital;
import software.visionary.vitalizr.api.VitalRepository;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodSugar.BloodSugar;
import software.visionary.vitalizr.bodyTemperature.BodyTemperature;
import software.visionary.vitalizr.oxygen.BloodOxygen;
import software.visionary.vitalizr.pulse.Pulse;
import software.visionary.vitalizr.weight.Weight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public final class Vitalizr {
    private static final VitalRepository<Vital> VITALS = new InMemoryVitalRepository();

    private Vitalizr() {
    }

    public static void storeWeightFor(final Weight toStore) {
        VITALS.save(toStore);
    }

    public static Collection<Weight> getWeightsFor(final Person toFind) {
        return getVitalFor(toFind, Weight.class);
    }

    private static <T extends Vital> Collection<T> getVitalFor(final Person toFind, final Class<T> queried) {
        return getVitalsMatching(queried, vital -> vital.belongsTo().equals(toFind) && queried.isAssignableFrom(vital.getClass()));
    }

    private static <T extends Vital> Collection<T> getVitalsMatching(final Class<T> queried, final Predicate<Vital> predicate) {
        final Collection<T> found = new ArrayList<>();
        VITALS.accept(vital -> {
            if (predicate.test(vital)) {
                found.add(queried.cast(vital));
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
        return getVitalsInInterval(person, interval, Weight.class);
    }

    private static <T extends Vital> Collection<T> getVitalsInInterval(final Person person, final Interval interval, final Class<T> queried) {
        return getVitalsMatching(queried, (vital) -> vital.belongsTo().equals(person) && interval.contains(vital.observedAt()) && queried.isAssignableFrom(vital.getClass()));
    }

    public static void storeBloodPressureFor(final BloodPressure toStore) {
        VITALS.save(toStore);
    }

    public static Collection<BloodPressure> getBloodPressuresFor(final Person person) {
        return getVitalFor(person, BloodPressure.class);
    }

    public static Collection<BloodPressure> getBloodPressuresInInterval(final Person person, final Interval interval) {
        return getVitalsInInterval(person, interval, BloodPressure.class);
    }

    public static void storePulseFor(final Pulse pulse) {
        VITALS.save(pulse);
    }

    public static Collection<Pulse> getPulsesFor(final Person person) {
        return getVitalFor(person, Pulse.class);
    }

    public static Collection<Pulse> getPulsesInInterval(final Person person, final Interval interval) {
        return getVitalsInInterval(person, interval, Pulse.class);
    }

    public static void storeBloodOxygenFor(final BloodOxygen oxygen) {
        VITALS.save(oxygen);
    }

    public static Collection<BloodOxygen> getBloodOxygensFor(final Person person) {
        return getVitalFor(person, BloodOxygen.class);
    }

    public static Collection<BloodOxygen> getBloodOxygensInInterval(final Person person, final Interval interval) {
        return getVitalsInInterval(person, interval, BloodOxygen.class);
    }

    public static void storeBloodSugarFor(final BloodSugar toStore) {
        VITALS.save(toStore);
    }

    public static Collection<BloodSugar> getBloodSugarsFor(final Person person) {
        return getVitalFor(person, BloodSugar.class);
    }

    public static Collection<BloodSugar> getBloodSugarsInInterval(final Person person, final Interval interval) {
        return getVitalsInInterval(person, interval, BloodSugar.class);
    }

    public static void storeTemperature(final BodyTemperature toStore) {
        VITALS.save(toStore);
    }

    public static Collection<BodyTemperature> getBodyTemperaturesFor(final Person person) {
        return getVitalFor(person, BodyTemperature.class);
    }

    public static Collection<BodyTemperature> getBodyTemperaturesInInterval(final Person person, final Interval interval) {
        return getVitalsInInterval(person, interval, BodyTemperature.class);
    }
}
