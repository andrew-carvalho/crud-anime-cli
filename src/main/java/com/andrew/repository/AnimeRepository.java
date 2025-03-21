package com.andrew.repository;

import com.andrew.domain.Anime;
import com.andrew.domain.ConnectionFactory;
import com.andrew.domain.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class AnimeRepository {

    public static List<Anime> findAll() {
        List<Anime> animeList = new ArrayList<>();
        String sqlQuery = """
                SELECT a.id, a.name, a.episodes, a.producer_id, p.name AS producer_name
                FROM anime a INNER JOIN producer p ON p.id = a.producer_id;
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                Producer producer = Producer.builder()
                        .id(resultSet.getInt("producer_id"))
                        .name(resultSet.getString("producer_name"))
                        .build();
                Anime anime = Anime.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .episodes(resultSet.getInt("episodes"))
                        .producer(producer)
                        .build();
                animeList.add(anime);
            }
        } catch (SQLException e) {
            log.error("Error while returning animes: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return animeList;
    }

}
