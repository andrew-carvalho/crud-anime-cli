package com.andrew.application;

import com.andrew.service.ProducerService;

import java.util.Scanner;

public class Program {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMainMenu();
            int userInput = Integer.parseInt(SCANNER.nextLine());

            if (userInput == 0) {
                break;
            }

            switch (userInput) {
                case 1:
                    ProducerService.chooseOption();
                    break;
                case 2:
                    // TODO: Anime menu
                    break;
                default:
                    System.out.println("Invalid option! Try again");
                    break;
            }
        }
    }

    public static void printMainMenu() {
        System.out.println("1 - Producers");
        System.out.println("2 - Anime");
        System.out.println("0 - Exit");
        System.out.print("Choose a option: ");
    }

}
