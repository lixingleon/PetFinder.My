package com.example.demo.Controller;

import com.example.demo.Model.TrainData;
import com.example.demo.Service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("api/Train")
public class TrainController {
    private TrainService trainService;
    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }
    @PostMapping
    public String getTrainResult(@RequestBody TrainData trainData){
        String result = trainService.train(trainData);
        return result;
    }
}
