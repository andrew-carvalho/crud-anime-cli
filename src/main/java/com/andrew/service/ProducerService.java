package com.andrew.service;

import com.andrew.domain.Producer;
import com.andrew.repository.ProducerRepository;

import java.util.List;

public class ProducerService {

    public static List<Producer> findByName(String name) {
        return ProducerRepository.findByName(name);
    }

    public static List<Producer> findAll() {
        return ProducerRepository.findAll();
    }

}
