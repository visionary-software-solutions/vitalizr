package software.visionary.vitalizr.weight;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class KilogramTest {
    @Test
    void nameIsKilogram() {
        Assertions.assertEquals(Kilogram.class.getSimpleName(), Kilogram.INSTANCE.getName());
    }

    @Test
    void symbolIsKg() {
        Assertions.assertEquals("kg", Kilogram.INSTANCE.getSymbol());
    }
}
