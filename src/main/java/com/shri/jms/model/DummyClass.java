package com.shri.jms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DummyClass implements Serializable {

    private static final long serialVersionUID = -574097107417445409L;
    private UUID uuid;
    private String message;
}
