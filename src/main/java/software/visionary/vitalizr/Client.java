package software.visionary.vitalizr;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(final String[] args) {
        if(args == null || args.length == 0) {
            listPeople();
        } else {
            addWeightToPerson(args);
        }
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

    private static void addWeightToPerson(final String[] args) {
        try (final Socket sock = new Socket(InetAddress.getLocalHost(), 13338);
             final BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
             final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))) {
            final String toSend = args[0];
            System.out.println("Sending command" + toSend);
            out.write(toSend);
            out.newLine();
            out.flush();
            final String received = in.readLine();
            System.out.println("received response  " + received);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
