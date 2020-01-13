package software.visionary.vitalizr;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ServiceLoader;
import java.util.function.BiConsumer;

public enum Iluvatar {
    INSTANCE;
    public static void main(final String[] args) throws UnknownHostException {
        System.out.println("first " + args[0] + " second " + args[1]);
        final NaturalNumber port = getPort(args[0]);
        final SocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), port.intValue());
        final Endpoint endpoint = new SingleThreadedSocketListener(socketAddress, getConsumer(args[1]));
        endpoint.start();
        Runtime.getRuntime().addShutdownHook(new Thread(endpoint::stop));
    }

    private static BiConsumer<InputStream, OutputStream> getConsumer(final String sought) {
        return ServiceLoader.load(BiConsumer.class).stream().filter(service -> service.type().getSimpleName().equalsIgnoreCase(sought)).findAny().orElseThrow(RuntimeException::new).get();
    }

    private static NaturalNumber getPort(final String portArg) {
        return new NaturalNumber(Integer.parseInt(portArg));
    }
}
