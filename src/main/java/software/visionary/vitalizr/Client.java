package software.visionary.vitalizr;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class Client {
    public static void main(final String[] args) {
        final String command = args[0];
        final String vital = args[1];
        final String[] params = Arrays.copyOfRange(args, 2, args.length);
        if (command.equalsIgnoreCase("List")) {
            if (vital.equalsIgnoreCase("Weights")) {
                getWeightsForId(params[0]);
            } else if (vital.equalsIgnoreCase("BMIs")) {
                getBMIsForId(params[0]);
            }
        } else if (command.equalsIgnoreCase("Add")) {
            if (vital.equalsIgnoreCase("Weight")) {
                addWeightToPerson(params[0], params[1], params[2]);
            } else if (vital.equalsIgnoreCase("BMI")) {
                addBMIToPerson(params[0], params[1]);
            }
        }
    }

    private static void getWeightsForId(final String arg) {
        doRequestReply(13339, String.format("%s\u0004", arg));
    }

    private static void getBMIsForId(final String arg) {
        doRequestReply(13341, String.format("%s\u0004", arg));
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

    private static void addWeightToPerson(final String weight, final String unit, final String person) {
        doRequestReply(13338, String.format("%s&%s&%s\u0004", weight, unit, person));
    }

    private static void addBMIToPerson(final String bmi, final String person) {
        doRequestReply(13338, String.format("%s&%s\u0004", bmi, person));
    }

}
