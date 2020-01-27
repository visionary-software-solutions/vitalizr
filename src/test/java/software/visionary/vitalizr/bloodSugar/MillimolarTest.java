package software.visionary.vitalizr.bloodSugar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MillimolarTest {
    @Test
    void nameIsMillimolar() {
        Assertions.assertEquals(Millimolar.class.getSimpleName(), Millimolar.INSTANCE.getName());
    }

    @Test
    void symbolIsmM() {
        Assertions.assertEquals("mM", Millimolar.INSTANCE.getSymbol());
    }
}
