package software.visionary.identifr;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

enum Client {
    AUTHENTICATE_WITH_PASSWORD(13377);
    private int port;

    Client(final int port) {
        this.port = port;
    }

    public static void main(final String[] args) {
        final Deque<String> deque = new ArrayDeque<>(Arrays.asList(args == null ? new String[0] : args));
        AUTHENTICATE_WITH_PASSWORD.execute(String.format("%s\uD83D\uDE94%s\u0004", deque.pop(), deque.pop()));

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
