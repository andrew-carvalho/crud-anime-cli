package com.andrew.application;

import lombok.extern.log4j.Log4j2;

import java.util.Scanner;

@Log4j2
public class Program {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int userInput;
        boolean shouldRun = true;

        while (shouldRun) {
            printMenu();
            userInput = Integer.parseInt(scanner.nextLine());
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
                System.out.print("Enter the producer´s name: ");
                String producerName = scanner.nextLine();
                break;
            case 2:
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

}
