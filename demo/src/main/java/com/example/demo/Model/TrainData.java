package com.example.demo.Model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "traindata")
public class TrainData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, name = "name")
    private String name;
    @Column(nullable = false, name = "path")
    private String path;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "TrainData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public TrainData() {
    }

    public TrainData(Long id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }
}
