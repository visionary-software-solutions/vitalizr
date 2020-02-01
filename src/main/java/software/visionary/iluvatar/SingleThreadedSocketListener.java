package software.visionary.iluvatar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Objects;
import java.util.function.BiConsumer;

final class SingleThreadedSocketListener implements Endpoint {
    private final SocketAddress address;
    private final BiConsumer<InputStream, OutputStream> sink;

    SingleThreadedSocketListener(final SocketAddress socks, final BiConsumer<InputStream, OutputStream> consumer) {
        this.address = Objects.requireNonNull(socks);
        this.sink = Objects.requireNonNull(consumer);
    }

    @Override
    public void start() {
        try (final ServerSocket server = new ServerSocket()) {
            server.bind(address);
            waitForConnections(server);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitForConnections(final ServerSocket server) {
        while (true) {
            try (final Socket connection = server.accept()) {
                while (connection.isConnected() && !connection.isClosed()) {
                    try (final InputStream received = connection.getInputStream();
                         final OutputStream sent = connection.getOutputStream()) {
                        sink.accept(received, sent);
                    } catch (final IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
            Thread.onSpinWait();
        }
    }

    @Override
    public void stop() {

    }
}
