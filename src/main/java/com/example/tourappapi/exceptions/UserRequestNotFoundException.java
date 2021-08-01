package com.example.tourappapi.exceptions;

public class UserRequestNotFoundException extends RuntimeException{
    public UserRequestNotFoundException(){
        super("Not found");
    }
}
