package software.visionary.vitalizr;

import software.visionary.vitalizr.api.Lifeform;
import software.visionary.vitalizr.api.Person;
import software.visionary.vitalizr.weight.MetricWeight;
import software.visionary.vitalizr.weight.Weight;

import java.time.Instant;
import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A CLI for demonstration.
 */
public final class Application {
    private Application() {
    }

    public static void main(final String[] args) {
        printMenu();
        final Scanner input = new Scanner(System.in);
        while (true) {
            switch (input.nextLine()) {
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
                    System.out.println("Please select from valid options");
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
        final Collection<Weight> result = Vitalizr.getWeightsFor(sought);
        displayWeights(result);
    }

    private static void displayWeights(final Collection<Weight> result) {
        if (result.isEmpty()) {
            System.out.println("No People stored");
        } else {
            result.stream().collect(Collectors.groupingBy(Weight::belongsTo)).forEach((person, weights) -> {
                display(person);
                weights.forEach(pw -> {
                    System.out.printf("%s %s %s %n", pw.observedAt(), pw.getQuantity().doubleValue(), pw.getUnit());
                });
            });
        }
    }

    private static void display(final Lifeform person) {
        System.out.printf("%s%n", person);
    }

    private static void addWeightToPerson(final String input) {
        final String delimiter = "&";
        final String[] tokens = input.split(delimiter);
        Vitalizr.storeWeight(new MetricWeight(Instant.now(), Integer.valueOf(tokens[1]), Human.createPerson(tokens[0])));
    }
}
