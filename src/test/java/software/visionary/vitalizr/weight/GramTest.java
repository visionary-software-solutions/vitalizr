package software.visionary.vitalizr.weight;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GramTest {
    @Test
    void nameIsGram() {
        Assertions.assertEquals(Gram.class.getSimpleName(), Gram.INSTANCE.getName());
    }

    @Test
    void symbolIsG() {
        Assertions.assertEquals("g", Gram.INSTANCE.getSymbol());
    }
}
