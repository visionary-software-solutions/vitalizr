package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Birthdate;
import software.visionary.vitalizr.api.EmailAddress;
import software.visionary.vitalizr.api.Name;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.bloodPressure.BloodPressure;
import software.visionary.vitalizr.bloodPressure.Combined;
import software.visionary.vitalizr.bloodPressure.Diastolic;
import software.visionary.vitalizr.bloodPressure.Systolic;
import software.visionary.vitalizr.weight.Gram;
import software.visionary.vitalizr.weight.Weight;

import java.time.Instant;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.temporal.ChronoUnit;

public class Fixtures {
    public static Weight weight() {
        return new Weight(Instant.now(), 99.8, new Gram());
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
}