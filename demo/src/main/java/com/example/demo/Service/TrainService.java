package com.example.demo.Service;

import com.example.demo.Model.TrainData;
import com.example.demo.dao.TrainDataDao;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


@Service
public class TrainService {
    static final String uploadPath = "uploads";
    public List<String> getMetaData(String name){
        File file = new File(uploadPath, name);
        List<String> list = new ArrayList<>();
        String[] name_split = name.split("\\.");
        double fileSize = file.length() / (1024.0 * 1024.0);
        fileSize = Math.floor(fileSize * 100) / 100;
        list.add(fileSize+"MB");
        if(name_split[name_split.length-1].equals("csv")){

            //获取file大小，行数，列数，字段名
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                CSVReader csvReader = new CSVReader(reader);
                String[] features = csvReader.readNext();
                int colNum = features.length;
                int rowNum = 1;
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    rowNum++;
                }
                list.add(rowNum+" rows");
                list.add(colNum+" columns");
                for(String feature: features){
                    list.add(feature);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
