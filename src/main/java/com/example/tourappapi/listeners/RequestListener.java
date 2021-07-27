package com.example.tourappapi.listeners;

import com.example.tourappapi.configs.RabbitmqConfig;
import com.example.tourappapi.dto.RequestDto;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.services.interfaces.AgentRequestService;
import com.example.tourappapi.services.interfaces.RequestService;
import com.example.tourappapi.utils.RequestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @RabbitListener(queues = RabbitmqConfig.QUEUE)
    public void consumeMessageFromQueue(RequestDto requestDto) throws JsonProcessingException {
        System.out.println(requestDto.getAnswers());
        Request request = Request.builder()
                .uuid(requestDto.getUuid())
                .answersJson( new ObjectMapper().writeValueAsString(requestDto.getAnswers())).build();
        request.setDeadline(RequestUtil.getDeadline(start, end , deadline));
        service.save(request);
        agentRequestService.createByRequest(request);
    }
}
