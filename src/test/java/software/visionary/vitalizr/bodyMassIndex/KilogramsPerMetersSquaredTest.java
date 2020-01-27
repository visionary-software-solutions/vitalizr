package software.visionary.vitalizr.bodyMassIndex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class KilogramsPerMetersSquaredTest {
    @Test
    void nameIsMilligramsPerDecilitre() {
        Assertions.assertEquals(KilogramsPerMetersSquared.class.getSimpleName(), KilogramsPerMetersSquared.INSTANCE.getName());
    }

    @Test
    void symbolIskgM2() {
        Assertions.assertEquals("kg/m^2", KilogramsPerMetersSquared.INSTANCE.getSymbol());
    }
}
