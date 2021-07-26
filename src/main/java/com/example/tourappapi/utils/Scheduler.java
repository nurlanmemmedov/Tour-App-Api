package com.example.tourappapi.utils;

import com.example.tourappapi.services.interfaces.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Scheduler {

    private RequestService service;

    public Scheduler(RequestService service){
        this.service = service;
    }

    @Scheduled(fixedRateString =  "${expiration.check.millisecond}")
    public void expireRequests() {
        try {
            service.updateExpiredRequests();
        }catch (Exception e){

        }
    }
}