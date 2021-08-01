package com.example.tourappapi.exceptions;

public class AlreadyHaveOfferException extends RuntimeException{
    public AlreadyHaveOfferException(){
        super("Offer exists");
    }
}
