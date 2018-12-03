package software.visionary.vitalizr;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;

final class SocketConnection implements Runnable {
    private final Socket socket;

    SocketConnection(final Socket theSocket) {
        socket = Objects.requireNonNull(theSocket);
    }

    @Override
    public void run() {
        while (socket.isConnected() && !socket.isClosed()) {
            try (final InputStream received = socket.getInputStream();
                 final OutputStream sent = socket.getOutputStream()) {
                new Request(received, sent).run();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
