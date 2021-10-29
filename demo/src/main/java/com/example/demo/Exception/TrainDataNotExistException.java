package com.example.demo.Exception;

public class TrainDataNotExistException extends RuntimeException{
    public TrainDataNotExistException(String message){
        super(message);
    }
}
