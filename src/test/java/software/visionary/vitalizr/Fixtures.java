package software.visionary.vitalizr;

import org.threeten.extra.Interval;
import software.visionary.vitalizr.api.Birthdate;
import software.visionary.vitalizr.api.EmailAddress;
import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Name;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.api.Unit;
import software.visionary.vitalizr.bloodSugar.BloodSugar;
import software.visionary.vitalizr.bloodSugar.MilligramsPerDecilitre;
import software.visionary.vitalizr.oxygen.BloodOxygen;
import software.visionary.vitalizr.pulse.Pulse;
import software.visionary.vitalizr.weight.MetricWeight;
import software.visionary.vitalizr.weight.Weight;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.ZoneOffset;

public class Fixtures {

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
}
