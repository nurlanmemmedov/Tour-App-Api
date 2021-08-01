package com.example.tourappapi.utils;

import com.example.tourappapi.models.Request;
import com.example.tourappapi.services.interfaces.RabbitmqService;
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
    private RabbitmqService rabbitmqService;

    public Scheduler(RequestService service, RabbitmqService rabbitmqService){
        this.service = service;
        this.rabbitmqService = rabbitmqService;
    }

    @Scheduled(fixedRateString =  "${expiration.check.millisecond}")
    public void expireRequests() {
        try {
            List<Request> requests = service.getAllExpiredRequests();
            requests.stream().forEach(r -> rabbitmqService.sendToExpiredQueue(r.getUuid()));
            service.updateExpiredRequests();
        }catch (Exception e){
        }
    }
}