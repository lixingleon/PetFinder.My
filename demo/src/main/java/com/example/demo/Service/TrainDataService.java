package com.example.demo.Service;

import com.example.demo.Exception.TrainDataEmptyNameException;
import com.example.demo.Exception.TrainDataNotExistException;
import com.example.demo.Model.TrainData;
import com.example.demo.dao.TrainDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

@Service
public class TrainDataService {
//    public static void main(String[] args) throws Exception {
//        String rlt = testTrain();
//        System.out.println(rlt);
//    }
    private TrainDataDao trainDataDao;
    @Autowired
    public TrainDataService(TrainDataDao trainDataDao){
        this.trainDataDao = trainDataDao;
    }

    public TrainData addTrainData(TrainData traindata){
        if(traindata.getName().isEmpty()){
            throw new TrainDataEmptyNameException("file name cannot be empty!");
        }
        return trainDataDao.save(traindata);
    }
    public TrainData updateTrainData(TrainData trainData){
        if(null == trainData.getId() || trainDataDao.existsById(trainData.getId())){
            throw new TrainDataNotExistException("file does not exist!");
        }
        return trainDataDao.save(trainData);
    }
    public List<TrainData> getAllTrainData(){
        return (List<TrainData>)trainDataDao.findAll();
    }

    public TrainData getTrainDataById(Long id){
        return trainDataDao.findById(id).get();
    }

    public void deleteTrainData(Long id){
        trainDataDao.deleteById(id);
    }


    public static String testTrain() {
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

        return "there's a 70% chance that this dog will be adopted within 3 days";
    }

}
