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
                deleteProducer();
                break;
            case 4:
                saveProducer();
                break;
            case 5:
                updateProducer();
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

    public static void deleteProducer() {
        System.out.print("Enter the producer´s ID: ");
        int producerId = Integer.parseInt(SCANNER.nextLine());
        Producer producer = ProducerService.findById(producerId);
        if (producer == null) {
            log.error("Producer with ID {} not found", producerId);
            return;
        }

        System.out.println(producer);
        System.out.print("Are you sure you want to delete this producer? [Y/N] ");
        String userResponse = SCANNER.nextLine();
        if ("Y".equalsIgnoreCase(userResponse)) {
            ProducerService.delete(producerId);
        }
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

    public static void saveProducer() {
        System.out.print("Enter new producer name: ");
        String producerName = SCANNER.nextLine();
        ProducerService.save(Producer.builder().name(producerName).build());
    }

    public static void updateProducer() {
        System.out.print("Enter producer ID: ");
        int producerId = Integer.parseInt(SCANNER.nextLine());

        Producer producer = ProducerService.findById(producerId);
        if (producer == null) {
            log.error("Producer with id {} not found", producerId);
            return;
        }

        System.out.println(producer);
        System.out.print("Enter new producer name (or empty to keep): ");
        String producerName = SCANNER.nextLine();

        if (producerName.isEmpty() || producerName.equals(producer.getName())) {
            return;
        }

        Producer updatedProducer = Producer.builder()
                .id(producerId)
                .name(producerName)
                .build();

        ProducerService.update(updatedProducer);
    }

}
