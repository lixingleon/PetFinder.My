package com.example.demo.dao;

import com.example.demo.Model.TrainData;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface TrainDataDao extends JpaRepository<TrainData, Long> {

    TrainData findByName(String name);

}
