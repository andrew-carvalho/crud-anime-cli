package com.andrew.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Anime {
    int id;
    String name;
    int episodes;
    Producer producer;
}
