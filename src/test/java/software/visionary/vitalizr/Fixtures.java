package software.visionary.vitalizr;

import org.threeten.extra.Interval;
import software.visionary.vitalizr.api.Birthdate;
import software.visionary.vitalizr.api.Caregiver;
import software.visionary.vitalizr.api.EmailAddress;
import software.visionary.vitalizr.api.Family;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.MedicalProvider;
import software.visionary.vitalizr.api.Name;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.bloodSugar.BloodSugar;
import software.visionary.vitalizr.bloodSugar.MilligramsPerDecilitre;
import software.visionary.vitalizr.bodyMassIndex.BodyMassIndex;
import software.visionary.vitalizr.bodyTemperature.BodyTemperature;
import software.visionary.vitalizr.bodyTemperature.ImperialTemperature;
import software.visionary.vitalizr.bodyWater.BodyWaterPercentage;
import software.visionary.vitalizr.oxygen.BloodOxygen;
import software.visionary.vitalizr.pulse.Pulse;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.ZoneOffset;

public class Fixtures {

    public static Person person() {
        return createPerson(new Name("Barbara Hidalgo-Toledo"),
                getBirthdate(Year.parse("1959"), MonthDay.of(Month.JANUARY, 9)),
                getEmailAddress(new Name("mommy"), new Name("mom.net")));
    }

    private static Person createPerson(final Name name, final Birthdate birthdate, final EmailAddress emailAddress) {
        return new Person() {
            @Override
            public Name getName() {
                return name;
            }

            @Override
            public Birthdate getBirthdate() {
                return birthdate;
            }

            @Override
            public EmailAddress getEmailAddress() {
                return emailAddress;
            }
        };
    }

    private static EmailAddress getEmailAddress(final Name userName, final Name topLevelDomain) {
        return new EmailAddress() {
            @Override
            public Name getName() {
                return userName;
            }

            @Override
            public Name getDomain() {
                return topLevelDomain;
            }
        };
    }

    private static Birthdate getBirthdate(final Year year, final MonthDay monthDay) {
        return new Birthdate() {
            @Override
            public Year getYear() {
                return year;
            }

            @Override
            public Month getMonth() {
                return monthDay.getMonth();
            }

            @Override
            public MonthDay getDay() {
                return monthDay;
            }
        };
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
        return new ImperialTemperature(person, v, instant);
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
        return new BodyMassIndex() {
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
                return instant;
            }
        };
    }

    public static BodyWaterPercentage bodyWaterPercentageAt(final double v, final Instant i, final Person p) {
        return new BodyWaterPercentage() {
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
}
