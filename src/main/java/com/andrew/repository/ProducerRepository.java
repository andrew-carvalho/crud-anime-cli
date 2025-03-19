package com.andrew.repository;

import com.andrew.domain.ConnectionFactory;
import com.andrew.domain.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ProducerRepository {

    public static List<Producer> findByName(String name) {
        List<Producer> producers = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = createPreparedStatementForFindByName(connection, name);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Producer producer = Producer.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();
                producers.add(producer);
            }
        } catch (SQLException e) {
            log.error("Error on retrieving producer with name {}", name);
            throw new RuntimeException(e);
        }

        return producers;
    }

    public static PreparedStatement createPreparedStatementForFindByName(Connection connection, String name) throws SQLException {
        String sqlQuery = "SELECT id, name FROM `anime`.`producer` WHERE `name` LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, String.format("%%%s%%", name));
        return preparedStatement;
    }

    public static List<Producer> findAll() {
        List<Producer> producers = new ArrayList<>();

        String sqlQuery = "SELECT id, name FROM `anime`.`producer`";

        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                Producer producer = Producer.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();
                producers.add(producer);
            }
        } catch (SQLException e) {
            log.error("Error on retrieving producers");
            throw new RuntimeException(e);
        }

        return producers;
    }

    public static Producer findById(int producerId) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = createPreparedStatementForFindById(connection, producerId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.next()) {
                return null;
            }
            return Producer.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement createPreparedStatementForFindById(Connection connection, int producerId) throws SQLException {
        String sqlQuery = "SELECT id, name FROM `anime`.`producer` WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, producerId);
        return preparedStatement;
    }

    public static void delete(int producerId) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = createPreparedStatementForDelete(connection, producerId);) {
            preparedStatement.execute();
            log.info("Producer {} deleted", producerId);
        } catch (SQLException e) {
            log.error("Error on deleting producer {}", producerId);
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement createPreparedStatementForDelete(Connection connection, int producerId) throws SQLException {
        String sqlQuery = "DELETE FROM `anime`.`producer` WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, producerId);
        return preparedStatement;
    }

    public static void save(Producer producer) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = createPreparedStatementForSave(connection, producer)) {
            preparedStatement.execute();
            log.info("Producer {} created", producer.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement createPreparedStatementForSave(Connection connection, Producer producer) throws SQLException {
        String sqlQuery = "INSERT INTO `anime`.`producer` (`name`) VALUES (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, String.format("'%s'", producer.getName()));
        return preparedStatement;
    }

}
