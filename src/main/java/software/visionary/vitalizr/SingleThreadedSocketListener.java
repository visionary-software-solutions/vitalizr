package software.visionary.vitalizr;

import java.io.IOException;
import java.net.*;
import java.util.Objects;

import java.util.concurrent.ExecutorService;

final class SingleThreadedSocketListener implements Endpoint {
    private final ExecutorService workers;
    private final SocketAddress address;

    SingleThreadedSocketListener(final SocketAddress socks, final ExecutorService workers) {
        this.address = Objects.requireNonNull(socks);
        this.workers = Objects.requireNonNull(workers);
    }

    @Override
    public void start() {
        try (final ServerSocket server = new ServerSocket()) {
            server.bind(address);
            workers.submit(() -> waitForConnections(server));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitForConnections(final ServerSocket server) {
        try (final Socket connection = server.accept()) {
            workers.submit(() -> new SocketConnection(connection));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        workers.shutdown();
    }
}
