package software.visionary.vitalizr;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Objects;
import java.util.function.BiFunction;

final class SingleThreadedSocketListener implements Endpoint {
    private final SocketAddress address;
    private final BiFunction<InputStream, OutputStream, Executable> factory;

    SingleThreadedSocketListener(final SocketAddress socks, final BiFunction<InputStream, OutputStream, Executable> factory) {
        this.address = Objects.requireNonNull(socks);
        this.factory = Objects.requireNonNull(factory);
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
                        factory.apply(received, sent).run();
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
