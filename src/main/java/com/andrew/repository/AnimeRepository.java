package com.andrew.repository;

import com.andrew.domain.Anime;
import com.andrew.domain.ConnectionFactory;
import com.andrew.domain.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
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

    public static List<Anime> findByName(String animeName) {
        List<Anime> animeList = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = createPreparedStatementForFindByName(connection, animeName);
             ResultSet resultSet = preparedStatement.executeQuery()) {
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
            throw new RuntimeException(e);
        }

        return animeList;
    }

    public static PreparedStatement createPreparedStatementForFindByName(Connection connection, String animeName) throws SQLException {
        String sqlQuery = """
                SELECT a.id, a.name, a.episodes, a.producer_id, p.name AS producer_name
                FROM anime a INNER JOIN producer p ON p.id = a.producer_id
                WHERE a.name LIKE ?;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, String.format("%%%s%%", animeName));
        return preparedStatement;
    }

    public static Anime findById(int animeId) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = createPreparedStatementForFindById(connection, animeId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                Producer producer = Producer.builder()
                        .id(resultSet.getInt("producer_id"))
                        .name(resultSet.getString("producer_name"))
                        .build();

                return Anime.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .episodes(resultSet.getInt("episodes"))
                        .producer(producer)
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static PreparedStatement createPreparedStatementForFindById(Connection connection, int animeId) throws SQLException {
        String sqlQuery = """
                SELECT a.id, a.name, a.episodes, a.producer_id, p.name AS producer_name
                FROM anime a INNER JOIN producer p ON p.id = a.producer_id
                WHERE a.id = ?;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, animeId);
        return preparedStatement;
    }

    public static void delete(int animeId) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = createPreparedStatementForDelete(connection, animeId)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement createPreparedStatementForDelete(Connection connection, int animeId) throws SQLException {
        String sqlQuery = "DELETE FROM anime WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, animeId);
        return preparedStatement;
    }

    public static void create(Anime anime) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = createPreparedStatementForCreate(connection, anime)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement createPreparedStatementForCreate(Connection connection, Anime anime) throws SQLException {
        String sqlQuery = "INSERT INTO anime (name, episodes, producer_id) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, anime.getName());
        preparedStatement.setInt(2, anime.getEpisodes());
        preparedStatement.setInt(3, anime.getProducer().getId());
        return preparedStatement;
    }

    public static void update(Anime anime) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = createPreparedStatementForUpdate(connection, anime)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement createPreparedStatementForUpdate(Connection connection, Anime anime) throws SQLException {
        String sqlQuery = "UPDATE anime SET name = ?, episodes = ?, producer_id = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, anime.getName());
        preparedStatement.setInt(2, anime.getEpisodes());
        preparedStatement.setInt(3, anime.getProducer().getId());
        preparedStatement.setInt(4, anime.getId());
        return preparedStatement;
    }

}
