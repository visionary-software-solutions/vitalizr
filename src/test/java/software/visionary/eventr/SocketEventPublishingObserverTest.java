package software.visionary.eventr;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import software.visionary.Randomizr;

import java.io.*;
import java.net.Socket;

class SocketEventPublishingObserverTest {
    @Test
    void rejectsNullSocket() {
        Assertions.assertThrows(NullPointerException.class, () -> new SocketEventPublishingObserver(null));
    }

    @Test
    void canPublishEventToSocket() throws IOException {
        // Given: A Socket to publish to
        final PipedInputStream pipeInput = new PipedInputStream();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(pipeInput));
        final BufferedOutputStream out = new BufferedOutputStream(new PipedOutputStream(pipeInput));
        final Socket socksy = Mockito.mock(Socket.class);
        Mockito.when(socksy.getOutputStream()).thenReturn(out);
        // When I construct a SocketEventPublishingObserver
        final SocketEventPublishingObserver toTest = new SocketEventPublishingObserver(socksy);
        // And: I have an event to publish
        final EventToStream e = new EventToStream();
        toTest.update(e);
        // Then: The event gets written to the Socket's outputstream
        Assertions.assertEquals(String.format("%s\u0004", e.toString()), reader.readLine());
    }

    private static final class EventToStream implements Event {
        private final String message;

        private EventToStream() {
            message = Randomizr.INSTANCE.createRandomAlphabeticString();
        }

        @Override
        public String toString() {
            return message;
        }
    }
}