package software.visionary.vitalizr;

import org.threeten.extra.Interval;
import software.visionary.vitalizr.api.Birthdate;
import software.visionary.vitalizr.api.EmailAddress;
import software.visionary.vitalizr.api.Name;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodPressure.Combined;
import software.visionary.vitalizr.bloodPressure.Diastolic;
import software.visionary.vitalizr.bloodPressure.Systolic;
import software.visionary.vitalizr.oxygen.BloodOxygen;
import software.visionary.vitalizr.pulse.Pulse;
import software.visionary.vitalizr.weight.Weight;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.ZoneOffset;

public class Fixtures {
    public static Weight weight() {
        return Weight.inKilograms(100, Instant.now());
    }

    public static Person person() {
        return new Person() {
            @Override
            public Name getName() {
                return new Name("Barbara Hidalgo-Toledo");
            }

            @Override
            public Birthdate getBirthdate() {
                return new Birthdate() {
                    @Override
                    public Year getYear() {
                        return Year.parse("1959");
                    }

                    @Override
                    public Month getMonth() {
                        return Month.JANUARY;
                    }

                    @Override
                    public MonthDay getDay() {
                        return MonthDay.of(Month.JANUARY, 9);
                    }
                };
            }

            @Override
            public EmailAddress getEmailAddress() {
                return new EmailAddress() {
                    @Override
                    public Name getName() {
                        return new Name("languwiz");
                    }

                    @Override
                    public Name getDomain() {
                        return new Name("gmail.com");
                    }
                };
            }
        };
    }

    public static BloodPressure bloodPressure() {
        return new Combined(new Systolic(Instant.now(), new NaturalNumber(153)), new Diastolic(Instant.now(), new NaturalNumber(80)));
    }

    public static Instant observationAtMidnightNDaysAgo(final int n) {
        return LocalDate.now().minusDays(n).atStartOfDay().toInstant(ZoneOffset.UTC);
    }

    public static Interval oneWeekAgoToNow() {
        return Interval.of(LocalDate.now().minusDays(7).atStartOfDay().toInstant(ZoneOffset.UTC), LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC));
    }

    public static Pulse pulse() {
        return pulseAt(91, Instant.now());
    }

    public static Pulse pulseAt(final int pulse, final Instant now) {
        return new Pulse() {
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

    public static BloodOxygen bloodOxygen() {
        return new BloodOxygen() {
            @Override
            public Number getQuantity() {
                return 94;
            }

            @Override
            public Instant observedAt() {
                return Instant.now();
            }
        };
    }
}
