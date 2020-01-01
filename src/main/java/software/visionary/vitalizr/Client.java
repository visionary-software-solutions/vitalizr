package software.visionary.vitalizr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(final String[] args) {
        listPeople();
    }

    private static void listPeople() {
        try (final Socket sock = new Socket(InetAddress.getLocalHost(), 13337);
             final BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()))) {
            final String received = in.readLine();
            System.out.println(received);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
