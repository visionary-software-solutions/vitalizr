package software.visionary.vitalizr.pulse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HeartbeatsPerMinuteTest {
    @Test
    void nameIsHeartbeatsPerMinute() {
        Assertions.assertEquals(HeartbeatsPerMinute.class.getSimpleName(), HeartbeatsPerMinute.INSTANCE.getName());
    }

    @Test
    void symbolIsBPM() {
        Assertions.assertEquals("bpm", HeartbeatsPerMinute.INSTANCE.getSymbol());
    }
}
