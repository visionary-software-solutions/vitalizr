package software.visionary.vitalizr;

import org.threeten.extra.Interval;
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

import java.time.*;
import java.util.concurrent.ThreadLocalRandom;

public class Fixtures {

    public static Person person() {
        return createPerson(new Name(createRandomName()),
                getBirthdate(Year.parse("1959"), MonthDay.of(Month.JANUARY, 9)),
                getEmailAddress(new Name("mommy"), new Name("mom.net")));
    }

    private static Person createPerson(final Name name, final Birthdate birthdate, final EmailAddress emailAddress) {
        return Human.createPerson(String.format("%s:%s:%s", name, birthdate, emailAddress));
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
        final String input = String.format("%s:%s:%s", name, birthday, email);
        return Human.createPerson(input);
    }

    private static String createRandomEmail() {
        return String.format("%s@%s.%s", createRandomAlphabeticString(), createRandomAlphabeticString(), createRandomAlphabeticString());
    }

    private static String createRandomBirthday() {
        return String.format("%04d-%02d-%02d", createRandomNumberBetween(1900, 2020), createRandomNumberBetween(1,12), createRandomNumberBetween(1, 31));
    }

    private static int createRandomNumberBetween(final int start, final int end) {
        return ThreadLocalRandom.current().nextInt(start, end);
    }

    private static String createRandomName() {
        return String.format("%s %s", createRandomAlphabeticString(), createRandomAlphabeticString());
    }

    private static String createRandomAlphabeticString() {
        final int length = ThreadLocalRandom.current().nextInt(10);
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(createRandomAlphabeticCharacter());
        }
        return builder.toString();
    }

    private static char createRandomAlphabeticCharacter() {
        return (char) (ThreadLocalRandom.current().nextInt(26) + 'a');
    }
}
