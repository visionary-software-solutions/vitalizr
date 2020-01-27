package software.visionary.identifr;

import org.junit.jupiter.api.Test;
import software.visionary.Randomizr;
import software.visionary.identifr.api.Authenticatable;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class EncryptWithCredentialsIntegrationTest {
    @Test
    void canEncryptDataWithStoredCredentials() {
        // Given : A stored Authenticatable
        final Authenticatable toStore = Fixtures.randomAuthenticatable();
        Identifir.INSTANCE.storeAuthenticatable(toStore);
        // And: A file with some data
        final CharSequence toWrite = Randomizr.INSTANCE.createRandomAlphabeticString();
        // When: I present that File to be encrypted for them
        final byte[] result = Identifir.INSTANCE.encryptFor(toStore.getID(), toWrite.toString().getBytes(StandardCharsets.UTF_8));
        // Then: I get back a new File
        // And: That new file is not the same as the old file
        // And: That file is encrypted with their credentials
        final byte[] decrypt = Identifir.INSTANCE.decryptFor(toStore.getID(), result);
        final String decrypted = new String(decrypt, StandardCharsets.UTF_8);
        assertEquals(toWrite, decrypted);
    }
}
