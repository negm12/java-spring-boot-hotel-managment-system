package com.global.hotel_managment.error;

public class RecordAlreadyExist extends RuntimeException {
    public RecordAlreadyExist(String message){
        super(message);
    }
}
