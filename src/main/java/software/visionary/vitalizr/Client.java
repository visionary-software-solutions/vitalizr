package software.visionary.vitalizr;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(final String[] args) {
        if(args.length == 1) {
            getWeightsForPerson(args[0]);
        } else if(args.length == 3){
            addWeightToPerson(args[0], args[1], args[2]);
        }
    }

    private static void getWeightsForPerson(final String arg) {
        try (final Socket sock = new Socket(InetAddress.getLocalHost(), 13339);
             final BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
             final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))) {
            final String toSend = String.format("%s\u0004", arg);
            System.out.println("Sending command " + toSend);
            out.write(toSend);
            out.flush();
            final String received = in.readLine();
            System.out.println("received response  " + received);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addWeightToPerson(final String weight, final String unit, final String person) {
        try (final Socket sock = new Socket(InetAddress.getLocalHost(), 13338);
             final BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
             final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))) {
            final String toSend = String.format("%s&%s&%s\u0004", weight, unit, person);
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
