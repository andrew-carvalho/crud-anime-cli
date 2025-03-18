package com.andrew.repository;

import com.andrew.domain.ConnectionFactory;
import com.andrew.domain.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

}
