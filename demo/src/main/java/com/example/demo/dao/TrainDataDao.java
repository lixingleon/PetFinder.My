package com.example.demo.dao;

import com.example.demo.Model.TrainData;

import java.util.List;

public interface TrainDataDao {
    List<TrainData> retrieveAllTrainData();
    TrainData getTrainDataByName(String name);
}
