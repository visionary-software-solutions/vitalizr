package software.visionary.vitalizr.bloodPressure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MillimetersOfMercuryTest {
    @Test
    void nameIsMillimetersOfMercury() {
        Assertions.assertEquals(MillimetersOfMercury.class.getSimpleName(), MillimetersOfMercury.INSTANCE.getName());
    }

    @Test
    void symbolIsmmHg() {
        Assertions.assertEquals("mm Hg", MillimetersOfMercury.INSTANCE.getSymbol());
    }
}
