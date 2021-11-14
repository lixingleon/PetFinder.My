package com.example.demo.Service;

import com.example.demo.Model.TrainData;
import com.example.demo.dao.TrainDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
@Service
public class TrainService {
    private TrainDataDao trainDataDao;
    @Autowired
    public TrainService(TrainDataDao trainDataDao){
        this.trainDataDao = trainDataDao;
    }
    public String train(TrainData response){
        TrainData trainData= trainDataDao.findByName(response.getName());
        //利用xgboost训练。
        String path = trainData.getPath();
        //训练过程
        System.out.println("start run python");
        try {
            Process proc = Runtime.getRuntime().exec("python ../models_manager/xgboost_.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line=null;
            // Todo: write line to return result, judge if there are multiple lines;
            while ((line=in.readLine())!=null){
                System.out.println("Train mse is: "+line);
            }
            proc.waitFor();
        } catch (Exception e) {return "False";}
        System.out.println("end run python");
        //返回训练评估值
        return "the mse of model is 1.2947489";
    }
}