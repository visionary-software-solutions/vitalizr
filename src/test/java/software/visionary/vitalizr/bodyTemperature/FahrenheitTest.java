package software.visionary.vitalizr.bodyTemperature;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FahrenheitTest {
    @Test
    void nameIsFahrenheit() {
        Assertions.assertEquals(Fahrenheit.class.getSimpleName(), Fahrenheit.INSTANCE.getName());
    }

    @Test
    void symbolIsF() {
        Assertions.assertEquals("F", Fahrenheit.INSTANCE.getSymbol());
    }
}
