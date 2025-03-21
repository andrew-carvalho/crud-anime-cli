package com.andrew.service;

import com.andrew.domain.Anime;
import com.andrew.domain.Producer;
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
        if (animeList.isEmpty()) {
            System.out.printf("No anime found with the name %s/n", animeName);
            return;
        }
        showAnimesFound(animeList);
    }

    public static Anime findById(int id) {
        return AnimeRepository.findById(id);
    }

    public static void delete() {
        System.out.print("Enter anime ID to delete: ");
        int animeId = Integer.parseInt(SCANNER.nextLine());

        Anime anime = findById(animeId);
        if (anime == null) {
            System.out.printf("No anime found with id %d\n", animeId);
            return;
        }

        System.out.println(anime);
        System.out.print("Are you sure you want to delete this anime? [Y/N] ");
        String userResponse = SCANNER.nextLine();
        if ("Y".equalsIgnoreCase(userResponse)) {
            AnimeRepository.delete(animeId);
        }
    }

    public static void create() {
        System.out.print("Enter producer's ID: ");
        int producerId = Integer.parseInt(SCANNER.nextLine());
        Producer producer = ProducerService.findById(producerId);
        if (producer == null) {
            System.out.printf("Producer with ID %d not found, try again.\n", producerId);
            return;
        }

        System.out.print("Enter anime name: ");
        String animeName = SCANNER.nextLine();
        if (animeName.isEmpty()) {
            System.out.println("Anime name cannot be empty!");
            return;
        }

        System.out.print("Enter number of episodes: ");
        int animeEpisodes = Integer.parseInt(SCANNER.nextLine());
        if (animeEpisodes <= 0) {
            System.out.println("Anime must contain at least one episode");
            return;
        }

        Anime anime = Anime.builder()
                .name(animeName)
                .episodes(animeEpisodes)
                .producer(producer)
                .build();

        AnimeRepository.create(anime);
    }

    public static void update() {
        System.out.print("Enter anime ID: ");
        int animeId = Integer.parseInt(SCANNER.nextLine());
        Anime anime = findById(animeId);
        if (anime == null) {
            System.out.printf("Anime with ID %d not found!\n", animeId);
            return;
        }

        System.out.println(anime);

        System.out.print("Enter new anime name (or empty to keep the same): ");
        String animeName = SCANNER.nextLine();
        if (animeName.isEmpty()) {
            animeName = anime.getName();
        }

        System.out.print("Enter new episodes quantity (or empty to keep the same): ");
        String animeEpisodesString = SCANNER.nextLine();

        int animeEpisodes;
        if (animeEpisodesString.isEmpty()) {
            animeEpisodes = anime.getEpisodes();
        } else {
            animeEpisodes = Integer.parseInt(animeEpisodesString);
            if (animeEpisodes <= 0) {
                System.out.println("Anime must contain at least one episode");
                return;
            }
        }

        System.out.print("Enter new Producer ID (or empty to keep the same): ");
        String producerIdString = SCANNER.nextLine();

        int producerId;
        if (producerIdString.isEmpty()) {
            producerId = anime.getProducer().getId();
        } else {
            producerId = Integer.parseInt(producerIdString);
        }

        Producer producer = ProducerService.findById(producerId);
        if (producer == null) {
            System.out.printf("Producer with ID %d not found, try again.\n", producerId);
            return;
        }

        Anime newAnime = Anime.builder()
                .id(animeId)
                .name(animeName)
                .episodes(animeEpisodes)
                .producer(producer)
                .build();

        AnimeRepository.update(newAnime);
    }

}
