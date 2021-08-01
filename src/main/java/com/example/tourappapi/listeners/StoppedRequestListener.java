package com.example.tourappapi.listeners;

import com.example.tourappapi.configs.RabbitmqConfig;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.services.interfaces.AgentRequestService;
import com.example.tourappapi.services.interfaces.RequestService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StoppedRequestListener {

    private RequestService service;
    private AgentRequestService agentRequestService;

    public StoppedRequestListener(RequestService service,
                                  AgentRequestService agentRequestService){
        this.service = service;
        this.agentRequestService = agentRequestService;
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
