package com.andrew.service;

import org.junit.Assert;
import org.junit.Test;

public class AnimeServiceTest {

    @Test
    public void FindByIdShouldReturnNullIfZero() {
        Assert.assertNull(AnimeService.findById(0));
    }

    @Test
    public void FindByIdShouldReturnNullIfNegative() {
        Assert.assertNull(AnimeService.findById(-1));
    }

}