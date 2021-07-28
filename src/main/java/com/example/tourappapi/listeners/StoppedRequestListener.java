package com.example.tourappapi.listeners;

import com.example.tourappapi.configs.RabbitmqConfig;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.services.interfaces.RequestService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StoppedRequestListener {

    private RequestService service;

    public StoppedRequestListener(RequestService service){
        this.service = service;
    }

    @Transactional
    @RabbitListener(queues = RabbitmqConfig.STOP)
    public void expiredRequestListener(String uuid) {
        try {
            Request request = service.getByUuid(uuid);
            request.setIsActive(false);
            service.save(request);
        }catch (Exception e){
        }
    }
}
