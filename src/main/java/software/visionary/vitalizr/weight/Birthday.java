package software.visionary.vitalizr.weight;

import software.visionary.vitalizr.api.Birthdate;

import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.Objects;

public final class Birthday implements Birthdate {
    private final Year year;
    private final Month month;
    private final MonthDay day;

    private Birthday(final Year year, final Month month, final MonthDay day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    static Birthdate createFrom(final String birthdate) {
        final String[] iso8601 = birthdate.split("-");
        final Month month = Month.of(Integer.valueOf(iso8601[1]));
        return new Birthday(Year.parse(iso8601[0]), month, MonthDay.of(month, Integer.valueOf(iso8601[2])));
    }

    @Override
    public String toString() {
        return "Birthday{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Birthday birthday = (Birthday) o;
        return Objects.equals(getYear(), birthday.getYear()) &&
                getMonth() == birthday.getMonth() &&
                Objects.equals(getDay(), birthday.getDay());
    }

    @Override
    public Year getYear() {
        return year;
    }

    @Override
    public Month getMonth() {
        return month;
    }

    @Override
    public MonthDay getDay() {
        return day;
    }

    @Override
    public int hashCode() {

        return Objects.hash(getYear(), getMonth(), getDay());
    }
}
