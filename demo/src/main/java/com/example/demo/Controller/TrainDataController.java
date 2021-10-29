package com.example.demo.Controller;

import com.example.demo.Exception.TrainDataEmptyNameException;
import com.example.demo.Model.TrainData;
import com.example.demo.Service.TrainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("api/TrainData")
public class TrainDataController {
    private TrainDataService trainDataService;
    @Autowired
    public TrainDataController(TrainDataService trainDataService) {
        this.trainDataService = trainDataService;
    }


    @GetMapping
    public List<TrainData> getAllTrainData(){
        return trainDataService.getAllTrainData();
    }

    @PostMapping
    public ResponseEntity<String> uploadTrainData(@RequestBody TrainData trainData){
        try{
            TrainData uploadedTrainData = trainDataService.addTrainData(trainData);
            return ResponseEntity.ok("uploadedTrainData"+trainData.toString());
        }catch(TrainDataEmptyNameException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }








}
