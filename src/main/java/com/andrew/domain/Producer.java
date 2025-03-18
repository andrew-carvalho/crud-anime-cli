package com.andrew.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Producer {
    Integer Id;
    String name;
}
