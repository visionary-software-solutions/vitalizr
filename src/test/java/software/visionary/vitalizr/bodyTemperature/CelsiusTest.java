package software.visionary.vitalizr.bodyTemperature;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CelsiusTest {
    @Test
    void nameIsCelsius() {
        Assertions.assertEquals(Celsius.class.getSimpleName(), Celsius.INSTANCE.getName());
    }

    @Test
    void symbolIsC() {
        Assertions.assertEquals("C", Celsius.INSTANCE.getSymbol());
    }
}
