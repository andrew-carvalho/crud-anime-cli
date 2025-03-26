package com.andrew.service;

import org.junit.Assert;
import org.junit.Test;

public class ProducerServiceTest {

    @Test
    public void FindByIdShouldReturnNullIfZero() {
        Assert.assertNull(ProducerService.findById(0));
    }

    @Test
    public void FindByIdShouldReturnNullIfNegative() {
        Assert.assertNull(ProducerService.findById(-1));
    }

}