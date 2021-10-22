package com.example.demo.TrainDataService;

import com.example.demo.Model.TrainData;
import com.example.demo.dao.ImpTrainDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class TrainDataService {
    public static void main(String[] args) throws Exception {
        String rlt = testTrain();
        System.out.println(rlt);
    }
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

    public static String testTrain() {
        System.out.println("start run python");
        try {
            Process proc = Runtime.getRuntime().exec("python /Users/chenyuqin/Desktop/21_fall_codes_and_relative/dsci551/project/models_manager/xgboost_.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line=null;
            // Todo: write line to return result, judge if there are multiple lines;
            while ((line=in.readLine())!=null){
                System.out.println("Train mse is: "+line);
            }
            proc.waitFor();
        } catch (Exception e) {return "False";}
        System.out.println("end run python");

        return "there's a 70% chance that this dog will be adopted within 3 days";
    }

}
