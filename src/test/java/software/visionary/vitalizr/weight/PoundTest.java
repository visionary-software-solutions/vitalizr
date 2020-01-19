package software.visionary.vitalizr.weight;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PoundTest {
    @Test
    void nameIsPound() {
        Assertions.assertEquals(Pound.class.getSimpleName(), Pound.INSTANCE.getName());
    }

    @Test
    void symbolIsLB() {
        Assertions.assertEquals("lb", Pound.INSTANCE.getSymbol());
    }
}
