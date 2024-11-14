package org.example.simulationapp;

import java.util.UUID;

public abstract class Vehicle {
    private final String id;

    public Vehicle() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public abstract String toString();
}
