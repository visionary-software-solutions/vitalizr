package software.visionary.identifr;

import software.visionary.iluvatar.Ainur;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class AuthenticateWithPassword extends Ainur {
    private static final String DELIMITER = "\uD83D\uDE94";

    public AuthenticateWithPassword() {
        Identifir.loadFromSecureLocation();
    }

    @Override
    protected void doCommand(final Scanner scanner, final BufferedWriter writer) {
        final String[] tokens = scanner.useDelimiter("\u0004").next().split(DELIMITER);
        final String soughtId = tokens[0];
        final String providedPassword = tokens[1];
        try {
            final UUID id = UUID.fromString(soughtId);
            final boolean result = Identifir.INSTANCE.authenticateWithPassword(id, providedPassword);
            writer.write(String.format("%d\u0004", result ? 1 : 0));
        } catch (final Exception e) {
            try {
                writer.write(String.format("%d\u0004", 0));
            } catch (final IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
