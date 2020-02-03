package software.visionary.vitalizr;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

enum Client {
    ADD_WEIGHT(13338),
    LIST_WEIGHT(13339),
    ADD_BMI(13340),
    LIST_BMI(13341),
    ADD_FAT(13342),
    LIST_FAT(13343);

    private int port;

    Client(final int port) {
        this.port = port;
    }

    public static void main(final String[] args) {
        final Deque<String> deque = new ArrayDeque<>(Arrays.asList(args == null ? new String[0] : args));
        final String toDispatch = String.format("%s_%s", deque.pop(), deque.pop());
        final Client client = Client.valueOf(toDispatch.toUpperCase());
        client.execute(createRequest(client, deque));

    }

    private static String createRequest(final Client client, final Deque<String> deque) {
        switch(client) {
            case ADD_WEIGHT: return String.format("%s&%s&%s\u0004", deque.pop(), deque.pop(), deque.pop());
            case LIST_WEIGHT:
            case LIST_BMI:
            case LIST_FAT:
                return String.format("%s\u0004", deque.pop());
            case ADD_BMI:
            case ADD_FAT:
                return String.format("%s&%s\u0004", deque.pop(), deque.pop());
            default: throw new UnsupportedOperationException("We do not support " + client.name());
        }
    }

    private void execute(final String request) {
        doRequestReply(port, request);
    }

    private static void doRequestReply(final int port, final String request) {
        try (final Socket sock = new Socket(InetAddress.getLocalHost(), port);
             final BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
             final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))) {
            System.out.println("Sending request " + request);
            out.write(request);
            out.flush();
            final String received = in.readLine();
            System.out.println("received response  " + received);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
