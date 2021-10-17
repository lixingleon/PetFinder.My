package com.example.demo.TrainDataService;

import com.example.demo.Model.TrainData;
import com.example.demo.dao.ImpTrainDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainDataService {
    private ImpTrainDataDao impTrainDataDao;
    @Autowired
    public TrainDataService(ImpTrainDataDao impTrainDataDao){
        this.impTrainDataDao = impTrainDataDao;
    }

    public List<TrainData> getAllTrainData(){
        return impTrainDataDao.retrieveAllTrainData();
    }

    public TrainData getTrainDataByName(String name){
        return impTrainDataDao.getTrainDataByName(name);
    }
    public String train(TrainData trainData){
        //利用xgboost训练。
        String path = trainData.getPath();
        //训练过程
        //...
        //返回训练评估值
        return "there's a 70% chance that this dog will be adopted within 3 days";
    }

}
