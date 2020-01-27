package software.visionary.vitalizr.oxygen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OxygenSaturationTest {
    @Test
    void nameIsOxygenSaturation() {
        Assertions.assertEquals(OxygenSaturation.class.getSimpleName(), OxygenSaturation.INSTANCE.getName());
    }

    @Test
    void symbolIsSpO2() {
        Assertions.assertEquals("SpO2", OxygenSaturation.INSTANCE.getSymbol());
    }
}
