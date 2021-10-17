package com.example.demo.Model;

import java.util.UUID;

public class TrainData {
    private UUID id;
    private String name;
    private String path;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public TrainData(UUID id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }
}
