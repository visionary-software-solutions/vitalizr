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
    LIST_FAT(13343),
    ADD_WATER(13344),
    LIST_WATER(13345),
    ADD_SUGAR(13346),
    LIST_SUGAR(13347),
    ADD_BP(13348),
    LIST_BP(13349),
    ADD_TEMP(13350),
    LIST_TEMP(13351),
    ADD_O2(13352),
    LIST_O2(13353),
    ADD_PULSE(13354),
    LIST_PULSE(13355);

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
            case ADD_WEIGHT:
            case ADD_TEMP:
                return String.format("%s&%s&%s\u0004", deque.pop(), deque.pop(), deque.pop());
            case LIST_WEIGHT:
            case LIST_BMI:
            case LIST_FAT:
            case LIST_WATER:
            case LIST_SUGAR:
            case LIST_BP:
            case LIST_TEMP:
            case LIST_O2:
            case LIST_PULSE:
                return String.format("%s\u0004", deque.pop());
            case ADD_BMI:
            case ADD_FAT:
            case ADD_WATER:
            case ADD_SUGAR:
            case ADD_BP:
            case ADD_O2:
            case ADD_PULSE:
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
