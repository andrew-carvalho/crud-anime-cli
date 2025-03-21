package com.andrew.service;

import com.andrew.domain.Anime;
import com.andrew.repository.AnimeRepository;

import java.util.List;
import java.util.Scanner;

public class AnimeService {

    public static final Scanner SCANNER = new Scanner(System.in);

    public static void printMenu() {
        System.out.println("1 - Get all animes");
        System.out.println("2 - Find anime by name");
        System.out.println("3 - Delete an anime");
        System.out.println("4 - Create new anime");
        System.out.println("5 - Update an anime");
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
                    // TODO: Delete
                    break;
                case 4:
                    // TODO: Create
                    break;
                case 5:
                    // TODO: Update
                    break;
                default:
                    System.out.println("Invalid option! Try again");
                    break;
            }
        }
    }

    public static void showAnimesFound(List<Anime> animeList) {
        System.out.println("Animes found: ");
        for (Anime anime : animeList) {
            System.out.printf("[%d] - %s (%d episodes) from %s\n",
                    anime.getId(), anime.getName(), anime.getEpisodes(), anime.getProducer().getName());
        }
        System.out.println("Press any key to continue...");
        SCANNER.nextLine();
    }

    public static void findAll() {
        List<Anime> animeList = AnimeRepository.findAll();
        if (animeList.isEmpty()) {
            System.out.println("No animes was found!");
            return;
        }
        showAnimesFound(animeList);
    }

    public static void findByName() {
        System.out.print("Enter anime name: ");
        String animeName = SCANNER.nextLine();
        List<Anime> animeList = AnimeRepository.findByName(animeName);
        if(animeList.isEmpty()) {
            System.out.printf("No anime found with the name %s/n", animeName);
            return;
        }
        showAnimesFound(animeList);
    }

}
