package com.andrew.service;

import com.andrew.domain.Producer;
import com.andrew.repository.ProducerRepository;

import java.util.List;
import java.util.Scanner;

public class ProducerService {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void printMenu() {
        System.out.println("1 - Get all producers");
        System.out.println("2 - Find producer by name");
        System.out.println("3 - Delete a producer");
        System.out.println("4 - Create new producer");
        System.out.println("5 - Update a producer");
        System.out.println("0 - Go back");
        System.out.print("Choose a option: ");
    }

    public static void chooseOption() {
        while (true) {
            printMenu();

            int userInput = Integer.parseInt(SCANNER.nextLine());

            if (userInput == 0) {
                break;
            }

            switch (userInput) {
                case 1:
                    findAll();
                    break;
                case 2:
                    findByName();
                    break;
                case 3:
                    delete();
                    break;
                case 4:
                    create();
                    break;
                case 5:
                    update();
                    break;
                default:
                    System.out.println("Invalid option! Try again");
                    break;
            }
        }
    }

    public static void showProducersFound(List<Producer> producers) {
        System.out.println("Producers found: ");
        for (Producer producer : producers) {
            System.out.printf("[%d] - %s\n", producer.getId(), producer.getName());
        }
        System.out.println("Press any key to continue...");
        SCANNER.nextLine();
    }

    public static void findByName() {
        System.out.print("Enter the producer´s name: ");
        String producerName = SCANNER.nextLine();
        List<Producer> producersFound = ProducerRepository.findByName(producerName);

        if (producersFound.isEmpty()) {
            System.out.println("No producers found with this name!");
        }

        showProducersFound(producersFound);
    }

    public static void findAll() {
        List<Producer> producers = ProducerRepository.findAll();
        showProducersFound(producers);
    }

    public static Producer findById(int producerId) {
        return ProducerRepository.findById(producerId);
    }

    public static void delete() {
        System.out.print("Enter the producer´s ID: ");
        int producerId = Integer.parseInt(SCANNER.nextLine());
        Producer producer = ProducerService.findById(producerId);
        if (producer == null) {
            System.out.printf("Producer with id %d not found\n", producerId);
            return;
        }

        System.out.println(producer);
        System.out.print("Are you sure you want to delete this producer? [Y/N] ");
        String userResponse = SCANNER.nextLine();
        if ("Y".equalsIgnoreCase(userResponse)) {
            ProducerRepository.delete(producerId);
        }
    }

    public static void create() {
        System.out.print("Enter new producer name: ");
        String producerName = SCANNER.nextLine();
        Producer producer = Producer.builder().name(producerName).build();
        ProducerRepository.create(producer);
    }

    public static void update() {
        System.out.print("Enter producer ID: ");
        int producerId = Integer.parseInt(SCANNER.nextLine());

        Producer producer = ProducerService.findById(producerId);
        if (producer == null) {
            System.out.printf("Producer with id %d not found\n", producerId);
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

        ProducerRepository.update(updatedProducer);
    }

}
