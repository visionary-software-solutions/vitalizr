package software.visionary.vitalizr;

import org.threeten.extra.Interval;
import software.visionary.vitalizr.api.*;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodSugar.BloodSugar;
import software.visionary.vitalizr.bodyFat.BodyFatPercentage;
import software.visionary.vitalizr.bodyMassIndex.BodyMassIndex;
import software.visionary.vitalizr.bodyTemperature.BodyTemperature;
import software.visionary.vitalizr.bodyWater.BodyWaterPercentage;
import software.visionary.vitalizr.notifications.Reminder;
import software.visionary.vitalizr.notifications.VitalNotification;
import software.visionary.vitalizr.notifications.VitalReminder;
import software.visionary.vitalizr.oxygen.BloodOxygen;
import software.visionary.vitalizr.pulse.Pulse;
import software.visionary.vitalizr.weight.Weight;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Vitalizr {
    private static final VitalRepository VITALS = new InMemoryVitalRepository();
    private static final TrustedContactRepository CONTACTS = new InMemoryTrustedContactRepository();
    private static final Repository<Reminder> REMINDERS = new InMemoryReminderRepository();
    private static final VitalSerializationStrategy<File> SERIALIZER = VitalAsGZipString.INSTANCE;

    private Vitalizr() {
    }

    public static void storeWeightFor(final Weight toStore) {
        storeVital(toStore);
    }

    public static void storeVital(final Vital pulse) {
        VITALS.save(pulse);
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
        storeVital(toStore);
    }

    public static Collection<BloodPressure> getBloodPressuresFor(final Person person) {
        return getVitalFor(person, BloodPressure.class);
    }

    public static Collection<BloodPressure> getBloodPressuresInInterval(final Person person, final Interval interval) {
        return getVitalsInInterval(person, interval, BloodPressure.class);
    }

    public static void storePulseFor(final Pulse pulse) {
        storeVital(pulse);
    }

    public static Collection<Pulse> getPulsesFor(final Person person) {
        return getVitalFor(person, Pulse.class);
    }

    public static Collection<Pulse> getPulsesInInterval(final Person person, final Interval interval) {
        return getVitalsInInterval(person, interval, Pulse.class);
    }

    public static void storeBloodOxygenFor(final BloodOxygen oxygen) {
        storeVital(oxygen);
    }

    public static Collection<BloodOxygen> getBloodOxygensFor(final Person person) {
        return getVitalFor(person, BloodOxygen.class);
    }

    public static Collection<BloodOxygen> getBloodOxygensInInterval(final Person person, final Interval interval) {
        return getVitalsInInterval(person, interval, BloodOxygen.class);
    }

    public static void storeBloodSugarFor(final BloodSugar toStore) {
        storeVital(toStore);
    }

    public static Collection<BloodSugar> getBloodSugarsFor(final Person person) {
        return getVitalFor(person, BloodSugar.class);
    }

    public static Collection<BloodSugar> getBloodSugarsInInterval(final Person person, final Interval interval) {
        return getVitalsInInterval(person, interval, BloodSugar.class);
    }

    public static void storeTemperature(final BodyTemperature toStore) {
        storeVital(toStore);
    }

    public static Collection<BodyTemperature> getBodyTemperaturesFor(final Person person) {
        return getVitalFor(person, BodyTemperature.class);
    }

    public static Collection<BodyTemperature> getBodyTemperaturesInInterval(final Person person, final Interval interval) {
        return getVitalsInInterval(person, interval, BodyTemperature.class);
    }

    public static void addFamilyMember(final Family family) {
        storeTrustedContact(family);
    }

    public static void storeTrustedContact(final TrustedContact contact) {
        CONTACTS.save(contact);
    }

    public static Collection<Family> getFamilyFor(final Person person) {
        return getTrustedContacts(person, Family.class);
    }

    private static <T extends TrustedContact> Collection<T> getTrustedContacts(final Person person, final Class<T> aClass) {
        final List<T> found = new ArrayList<>();
        CONTACTS.accept((TrustedContact tc) -> {
            if (tc.connectedTo().equals(person) && aClass.isAssignableFrom(tc.getClass())) {
                found.add(aClass.cast(tc));
            }
        });
        return found;
    }

    public static void addMedicalProvider(final MedicalProvider provider) {
        storeTrustedContact(provider);
    }

    public static Collection<MedicalProvider> getMedicalProvidersFor(final Person person) {
        return getTrustedContacts(person, MedicalProvider.class);
    }

    public static void addCaregiver(final Caregiver giver) {
        storeTrustedContact(giver);
    }

    public static Collection<Caregiver> getCaregiversFor(final Person person) {
        return getTrustedContacts(person, Caregiver.class);
    }

    public static Collection<VitalNotification> createNotificationsForTrustedContacts(final Person person) {
        final Collection<VitalNotification> notifications = new ArrayList<>();
        CONTACTS.accept(tc -> {
            if (tc.connectedTo().equals(person)) {
                notifications.addAll(getVitalsMatching(Vital.class, (vital) -> vital.belongsTo().equals(person))
                        .stream()
                        .map(v -> new VitalNotification(v, tc))
                        .collect(Collectors.toList()));
            }
        });
        return notifications;
    }

    public static <T extends Vital> void addReminderForVital(final Person person, final Class<T> toRemindAbout, final Instant when) {
        REMINDERS.save(new VitalReminder(when, person, toRemindAbout));
    }

    public static Collection<VitalReminder> getRemindersFor(final Person person) {
        final List<VitalReminder> saved = new ArrayList<>();
        REMINDERS.accept(reminder -> {
            if (reminder instanceof VitalReminder && reminder.target().equals(person)) {
                saved.add((VitalReminder) reminder);
            }
        });
        return saved;
    }

    public static void loadVitalsFromFile(final File data) {
        SERIALIZER.deserialize(data).forEach(VITALS::save);
    }

    public static void saveVitalsToFile(final File data) {
        final Collection<Vital> toWrite = new ArrayList<>();
        VITALS.accept(toWrite::add);
        SERIALIZER.serialize(toWrite, data);
    }

    public static void storeBodyMassIndexFor(final BodyMassIndex bodyMassIndex) {
        storeVital(bodyMassIndex);
    }

    public static Collection<BodyMassIndex> getBodyMassIndicesInInterval(final Person person, final Interval interval) {
        return getVitalsInInterval(person, interval, BodyMassIndex.class);
    }

    public static Collection<BodyMassIndex> getBodyMassIndicesFor(final Person person) {
        return getVitalFor(person, BodyMassIndex.class);
    }

    public static void storeBodyWaterPercentageFor(final BodyWaterPercentage toStore) {
        storeVital(toStore);
    }

    public static Collection<BodyWaterPercentage> getBodyWaterPercentagesFor(final Person person) {
        return getVitalFor(person, BodyWaterPercentage.class);
    }

    public static Collection<BodyWaterPercentage> getBodyWaterPercentagesInInterval(final Person person, final Interval interval) {
        return getVitalsInInterval(person, interval, BodyWaterPercentage.class);
    }

    public static void storeBodyFatPercentageFor(final BodyFatPercentage toStore) {
        storeVital(toStore);
    }

    public static Collection<BodyFatPercentage> getBodyFatPercentagesFor(final Person person) {
        return getVitalFor(person, BodyFatPercentage.class);
    }

    public static Collection<BodyFatPercentage> getBodyFatPercentagesInInterval(final Person person, final Interval interval) {
        return getVitalsInInterval(person, interval, BodyFatPercentage.class);
    }
}
