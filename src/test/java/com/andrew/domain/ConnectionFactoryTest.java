package com.andrew.domain;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

public class ConnectionFactoryTest {

    @Test
    public void getConnectionShouldReturnAnObject() {
        Connection connection = ConnectionFactory.getConnection();
        Assert.assertNotNull(connection);
    }

}