package software.visionary.eventr;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

final class SocketEventPublishingObserver implements Observer {
    private static final Logger LOG = Logger.getLogger(SocketEventPublishingObserver.class.getSimpleName());
    private final Socket publishTo;

    public SocketEventPublishingObserver(final Socket socket) {
        publishTo = Objects.requireNonNull(socket);
    }

    @Override
    public void update(final Event event) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(publishTo.getOutputStream()))) {
            writer.write(String.format("%s\u0004", event.toString()));
        } catch (final IOException e) {
            LOG.log(Level.WARNING, "problem writing event ", e);
        }
    }
}
