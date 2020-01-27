package software.visionary.vitalizr.bloodSugar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MilligramsPerDecilitreTest {
    @Test
    void nameIsMilligramsPerDecilitre() {
        Assertions.assertEquals(MilligramsPerDecilitre.class.getSimpleName(), MilligramsPerDecilitre.INSTANCE.getName());
    }

    @Test
    void symbolIsMgDl() {
        Assertions.assertEquals("mg/dL", MilligramsPerDecilitre.INSTANCE.getSymbol());
    }
}
