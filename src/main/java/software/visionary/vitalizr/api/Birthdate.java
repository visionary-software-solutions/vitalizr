package software.visionary.vitalizr.api;

import java.time.Month;
import java.time.MonthDay;
import java.time.Year;

public interface Birthdate {
    Year getYear();
    Month getMonth();
    MonthDay getDay();
}
