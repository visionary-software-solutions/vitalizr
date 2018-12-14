package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.weight.Human;
import software.visionary.vitalizr.weight.PersonWeight;
import software.visionary.vitalizr.weight.Weight;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * The entry point of the application.
 * Starts an endpoint and busy beavers.
 */
public final class Application {
    private Application() {
    }

    public static void main(final String[] args) throws UnknownHostException {
        final Integer numberOfThreads = (args.length > 0) ? Integer.parseInt(args[0]) : Runtime.getRuntime().availableProcessors() * 2;
        System.out.printf("The number of threads is %d%n", numberOfThreads);
        final NaturalNumber port = new NaturalNumber((args.length > 0) ? Integer.parseInt(args[1]) : 13337);
        System.out.printf("The port is %s%n", port);
        final SocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), port.intValue());
        final Endpoint endpoint = new SingleThreadedSocketListener(socketAddress, Executors.newFixedThreadPool(port.intValue()));
        endpoint.start();
        Runtime.getRuntime().addShutdownHook(new Thread(endpoint::stop));
        busyWait();
    }

    private static void busyWait() {
        printMenu();
        final Scanner input = new Scanner(System.in);
        while (true) {
            switch (input.nextLine()) {
                case "1":
                    listPeople();
                    break;
                case "2":
                    addWeightToPerson(input.nextLine());
                    break;
                case "3":
                    getWeightsForPerson(input.nextLine());
                    break;
                case "q":
                case "Q":
                    System.exit(1);
                default:
                    throw new UnsupportedOperationException("Please select from valid options");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Welcome to Vitalzr!");
        System.out.println("Please select from the following options");
        System.out.println("1. List People");
        System.out.println("2. Add Weight for Person");
        System.out.println("3. Get Weights for Person");
        System.out.println("Q. Quit");
    }

    private static void getWeightsForPerson(final String input) {
        final Person sought = Human.createPerson(input);
        final Collection<PersonWeight> result = Vitalizr.getWeightsFor(sought);
        displayWeights(result);
    }

    private static void displayWeights(final Collection<PersonWeight> result) {
        if (result.isEmpty()) {
            System.out.println("No People stored");
        } else {
            result.stream().collect(Collectors.groupingBy(PersonWeight::getPerson)).forEach((person, weights) -> {
                display(person);
                weights.forEach(pw -> {
                    System.out.printf("%s %s %s %n", pw.observedAt(), pw.getQuantity().doubleValue(), pw.getUnit());
                });
            });
        }
    }

    private static void display(final Person person) {
        System.out.printf("%s%n", person);
    }

    private static void addWeightToPerson(final String input) {
        final String delimiter = "&";
        final String[] tokens = input.split(delimiter);
        Vitalizr.storeWeightFor(Human.createPerson(tokens[0]), Weight.inKilograms(Integer.valueOf(tokens[1]), Instant.now()));
    }

    private static void listPeople() {
        display(Vitalizr.listPeople());
    }

    private static void display(final Collection<Person> people) {
        if (people.isEmpty()) {
            System.out.println("No People stored");
        } else {
            people.forEach(Application::display);
        }
    }

}
