package software.visionary.vitalizr;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

final class Request implements Runnable {
    private final InputStream received;
    private final OutputStream sent;

    Request(final InputStream received, final OutputStream sent) {
        this.received = Objects.requireNonNull(received);
        this.sent = Objects.requireNonNull(sent);
    }

    @Override
    public void run() {
        try {
            final String asString = new String(received.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println(asString);
            sent.write(String.format("Got %s %n", asString).getBytes(StandardCharsets.UTF_8));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
