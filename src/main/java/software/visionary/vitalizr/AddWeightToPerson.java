package software.visionary.vitalizr;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public final class AddWeightToPerson {
    private static final int PORT = 13338;

    private AddWeightToPerson() {}

    public static void main(final String[] args) throws UnknownHostException {
        final NaturalNumber port = getPort(args);
        System.out.printf("The port is %s%n", port);
        final SocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), port.intValue());
        final Endpoint endpoint = new SingleThreadedSocketListener(socketAddress, new AddWeightToPersonFactory());
        endpoint.start();
        Runtime.getRuntime().addShutdownHook(new Thread(endpoint::stop));
    }

    private static NaturalNumber getPort(final String[] args) {
        return new NaturalNumber((args.length > 0) ? Integer.parseInt(args[1]) : PORT);
    }
}
