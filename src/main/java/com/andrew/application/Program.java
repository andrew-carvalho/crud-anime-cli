package com.andrew.application;

import com.andrew.domain.Producer;
import com.andrew.service.ProducerService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Scanner;

@Log4j2
public class Program {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int userInput;
        boolean shouldRun = true;

        while (shouldRun) {
            printMenu();
            userInput = Integer.parseInt(SCANNER.nextLine());
            shouldRun = chooseAction(userInput);
        }
    }

    public static void printMenu() {
        System.out.println("--------------------------");
        System.out.println("MENU");
        System.out.println("--------------------------");
        System.out.println("0 - Exit program");
        System.out.println("1 - Find producer by name");
        System.out.println("2 - Find all producers");
        System.out.println("3 - Delete a producer");
        System.out.println("4 - Save new producer");
        System.out.println("5 - Update producer");
        System.out.println("--------------------------");
        System.out.print("Choose: ");
    }

    public static boolean chooseAction(int userInput) {
        switch (userInput) {
            case 1:
                findProducerByName();
                break;
            case 2:
                findAllProducers();
                break;
            case 3:
            case 4:
            case 5:
                System.out.println("To be implemented.");
                break;
            case 0:
                return false;
            default:
                log.error("Invalid input! Try again");
                break;
        }
        return true;
    }

    public static void findProducerByName() {
        System.out.print("Enter the producer´s name: ");
        String producerName = SCANNER.nextLine();
        List<Producer> producersFound = ProducerService.findByName(producerName);
        showProducersFound(producersFound);
    }

    public static void findAllProducers() {
        List<Producer> producers = ProducerService.findAll();
        showProducersFound(producers);
    }

    public static void showProducersFound(List<Producer> producers) {
        System.out.println("--------------------------");
        System.out.println("Producers found: ");
        System.out.println("--------------------------");
        for (Producer producer : producers) {
            System.out.println(producer);
        }
        System.out.println("--------------------------");
        System.out.println("Press Enter to continue..");
        SCANNER.nextLine();
    }

}
