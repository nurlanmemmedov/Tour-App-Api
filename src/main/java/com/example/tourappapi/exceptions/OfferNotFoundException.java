package com.example.tourappapi.exceptions;

public class OfferNotFoundException extends RuntimeException{
    public OfferNotFoundException(){
        super("Not found");
    }
}
