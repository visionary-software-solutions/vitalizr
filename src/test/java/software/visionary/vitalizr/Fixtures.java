package software.visionary.vitalizr;

import org.threeten.extra.Interval;
import software.visionary.Randomizr;
import software.visionary.vitalizr.api.*;
import software.visionary.vitalizr.bloodSugar.BloodSugar;
import software.visionary.vitalizr.bloodSugar.MilligramsPerDecilitre;
import software.visionary.vitalizr.bodyFat.BodyFatPercentage;
import software.visionary.vitalizr.bodyMassIndex.BodyMassIndex;
import software.visionary.vitalizr.bodyMassIndex.QueteletIndex;
import software.visionary.vitalizr.bodyTemperature.BodyTemperature;
import software.visionary.vitalizr.bodyTemperature.ImperialTemperature;
import software.visionary.vitalizr.bodyWater.BodyWaterPercentage;
import software.visionary.vitalizr.oxygen.BloodOxygen;
import software.visionary.vitalizr.pulse.Pulse;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Fixtures {

    static {
        instrumentForIntegrationTesting();
    }

    private static void instrumentForIntegrationTesting() {
        final String directory = System.getProperty("user.dir");
        final Path root = Paths.get(directory, "tmpTestingDir");
        Vitalizr.setPersister(new VitalPersister(root, Vitalizr.getVitalsMatching(Vital.class, Objects::nonNull)));
        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> {
                    try {
                        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
                            @Override
                            public FileVisitResult visitFile(Path file,
                                                             @SuppressWarnings("unused") BasicFileAttributes attrs)
                            throws IOException {
                                Files.delete(file);
                                return FileVisitResult.CONTINUE;
                            }
                            @Override
                            public FileVisitResult postVisitDirectory(Path dir, IOException e)
                            throws IOException {
                                if (e == null) {
                                    Files.delete(dir);
                                    return FileVisitResult.CONTINUE;
                                }
                                // directory iteration failed
                                throw e;
                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to delete "+root, e);
                    }
                }));
    }

    public static Person person() {
        return createPerson(new Name(createRandomName()),
                getBirthdate(Year.parse("1959"), MonthDay.of(Month.JANUARY, 9)),
                getEmailAddress(new Name("mommy"), new Name("mom.net")));
    }

    private static Person createPerson(final Name name, final Birthdate birthdate, final EmailAddress emailAddress) {
        return Human.createPerson(String.format("%s:%s:%s:%s", UUID.randomUUID().toString(), name, birthdate, emailAddress));
    }

    private static EmailAddress getEmailAddress(final Name userName, final Name topLevelDomain) {
        return PersonalEmail.createFrom(String.format("%s@%s", userName, topLevelDomain));
    }

    private static Birthdate getBirthdate(final Year year, final MonthDay monthDay) {
        return Birthday.createFrom(String.format("%s-%s-%s", year, monthDay.getMonth().getValue(), monthDay.getDayOfMonth()));
    }

    public static Instant observationAtMidnightNDaysAgo(final int n) {
        return LocalDate.now().minusDays(n).atStartOfDay().toInstant(ZoneOffset.UTC);
    }

    public static Interval oneWeekAgoToNow() {
        return Interval.of(LocalDate.now().minusDays(7).atStartOfDay().toInstant(ZoneOffset.UTC), LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC));
    }

    public static Pulse pulseAt(final int pulse, final Instant now, final Lifeform lifeform) {
        return new Pulse() {
            @Override
            public Unit getUnit() {
                return Unit.NONE.INSTANCE;
            }

            @Override
            public Lifeform belongsTo() {
                return lifeform;
            }

            @Override
            public Number getQuantity() {
                return pulse;
            }

            @Override
            public Instant observedAt() {
                return now;
            }
        };
    }

    public static BloodOxygen oxygenAt(final int o2, final Instant instant, final Lifeform lifeform) {
        return new BloodOxygen() {
            @Override
            public Unit getUnit() {
                return Unit.NONE.INSTANCE;
            }

            @Override
            public Lifeform belongsTo() {
                return lifeform;
            }

            @Override
            public Number getQuantity() {
                return o2;
            }

            @Override
            public Instant observedAt() {
                return instant;
            }
        };
    }

    public static BloodSugar bloodSugarAt(final int level, final Instant instant, final Lifeform lifeform) {
        return new BloodSugar() {
            @Override
            public Lifeform belongsTo() {
                return lifeform;
            }

            @Override
            public Number getQuantity() {
                return level;
            }

            @Override
            public Instant observedAt() {
                return instant;
            }

            @Override
            public Unit getUnit() {
                return MilligramsPerDecilitre.INSTANCE;
            }
        };
    }

    public static BodyTemperature temperatureAt(final double v, final Instant instant, final Person person) {
        return new ImperialTemperature(instant, v, person);
    }

    public static Family family(final Person person) {
        return new Family() {
            @Override
            public UUID getID() {
                return null;
            }

            private final Person whoIs = Fixtures.createPerson(new Name("Nick Vaidyanathan"),
                    Fixtures.getBirthdate(Year.parse("1985"), MonthDay.of(6, 11)),
                    Fixtures.getEmailAddress(new Name("master"), new Name("debater.com")));

            @Override
            public Person connectedTo() {
                return person;
            }

            @Override
            public EmailAddress getEmailAddress() {
                return whoIs.getEmailAddress();
            }

            @Override
            public Name getName() {
                return whoIs.getName();
            }

            @Override
            public Birthdate getBirthdate() {
                return whoIs.getBirthdate();
            }
        };
    }

    public static MedicalProvider doctor(final Person person) {
        return new MedicalProvider() {
            @Override
            public UUID getID() {
                return null;
            }

            private final Person whoIs = Fixtures.createPerson(new Name("Dr. Anthony Dash"),
                    Fixtures.getBirthdate(Year.parse("1957"), MonthDay.of(7, 13)),
                    Fixtures.getEmailAddress(new Name("doctor"), new Name("good.org")));

            @Override
            public Person connectedTo() {
                return person;
            }

            @Override
            public EmailAddress getEmailAddress() {
                return whoIs.getEmailAddress();
            }

            @Override
            public Name getName() {
                return whoIs.getName();
            }

            @Override
            public Birthdate getBirthdate() {
                return whoIs.getBirthdate();
            }
        };
    }

    public static Caregiver caregiver(final Person person) {
        return new Caregiver() {
            @Override
            public UUID getID() {
                return null;
            }

            private final Person whoIs = Fixtures.createPerson(new Name("Mandy McCray"),
                    Fixtures.getBirthdate(Year.parse("1978"), MonthDay.of(10, 22)),
                    Fixtures.getEmailAddress(new Name("caregiver"), new Name("care.org")));

            @Override
            public Person connectedTo() {
                return person;
            }

            @Override
            public EmailAddress getEmailAddress() {
                return whoIs.getEmailAddress();
            }

            @Override
            public Name getName() {
                return whoIs.getName();
            }

            @Override
            public Birthdate getBirthdate() {
                return whoIs.getBirthdate();
            }
        };
    }

    public static BodyMassIndex bmiAt(final double v, final Instant instant, final Person person) {
        return new QueteletIndex(instant, v, person);
    }

    public static BodyWaterPercentage bodyWaterPercentageAt(final double v, final Instant i, final Person p) {
        return new BodyWaterPercentage() {
            @Override
            public Unit getUnit() {
                return Unit.NONE.INSTANCE;
            }

            @Override
            public Lifeform belongsTo() {
                return p;
            }

            @Override
            public Number getQuantity() {
                return v;
            }

            @Override
            public Instant observedAt() {
                return i;
            }
        };
    }

    public static BodyFatPercentage bodyFatPercentageAt(final double v, final Instant i, final Person person) {
        return new BodyFatPercentage() {
            @Override
            public Unit getUnit() {
                return Unit.NONE.INSTANCE;
            }

            @Override
            public Lifeform belongsTo() {
                return person;
            }

            @Override
            public Number getQuantity() {
                return v;
            }

            @Override
            public Instant observedAt() {
                return i;
            }
        };
    }

    public static Person createRandomPerson() {
        final String name = createRandomName();
        final String birthday = createRandomBirthday();
        final String email = createRandomEmail();
        final String input = String.format("%s:%s:%s:%s", UUID.randomUUID().toString(), name, birthday, email);
        return Human.createPerson(input);
    }

    private static String createRandomEmail() {
        return String.format("%s@%s.%s", Randomizr.INSTANCE.createRandomAlphabeticString(), Randomizr.INSTANCE.createRandomAlphabeticString(), Randomizr.INSTANCE.createRandomAlphabeticString());
    }

    private static String createRandomBirthday() {
        final LocalDate start = LocalDate.of(1900, Month.JANUARY, 1);
        final LocalDate end = LocalDate.now().minus(18, ChronoUnit.YEARS);
        final long randomDay = ThreadLocalRandom.current().nextLong(start.toEpochDay(), end.toEpochDay());
        final LocalDate date = LocalDate.ofEpochDay(randomDay);
        return date.toString();
    }

    private static String createRandomName() {
        return String.format("%s %s", Randomizr.INSTANCE.createRandomAlphabeticString(), Randomizr.INSTANCE.createRandomAlphabeticString());
    }
}
