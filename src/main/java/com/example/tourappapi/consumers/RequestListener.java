package com.example.tourappapi.consumers;

import com.example.tourappapi.configs.RabbitmqConfig;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.services.interfaces.AgentRequestService;
import com.example.tourappapi.services.interfaces.RequestService;
import com.example.tourappapi.utils.RequestUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RequestListener {

    private RequestService service;
    private AgentRequestService agentRequestService;
    @Value("${request.deadline}")
    private Integer deadline;
    @Value("${workinghours.start}")
    private Integer start;
    @Value("${workinghours.end}")
    private Integer end;

    public RequestListener(RequestService service,
                           AgentRequestService agentRequestService){
        this.service = service;
        this.agentRequestService = agentRequestService;
    }

    @RabbitListener(queues = RabbitmqConfig.REQUESTQUEUE)
    public void consumeMessageFromQueue(Request request)  {
        request.setDeadline(RequestUtil.getDeadline(start, end , deadline));
        service.save(request);
        agentRequestService.createByRequest(request);
    }
}
