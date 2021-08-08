package com.example.tourappapi.listeners;

import com.example.tourappapi.configs.RabbitmqConfig;
import com.example.tourappapi.dto.RequestDto;
import com.example.tourappapi.dto.SelectedOfferDto;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.models.ClientInfo;
import com.example.tourappapi.models.Offer;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.services.interfaces.AgentRequestService;
import com.example.tourappapi.services.interfaces.ClientInfoService;
import com.example.tourappapi.services.interfaces.OfferService;
import com.example.tourappapi.utils.RequestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SelectedOfferListener {

    private ClientInfoService service;
    private OfferService offerService;
    private AgentRequestService agentRequestService;

    public SelectedOfferListener(ClientInfoService service,
                                 OfferService offerService,
                                 AgentRequestService agentRequestService){
        this.service = service;
        this.offerService = offerService;
        this.agentRequestService = agentRequestService;
    }

    @RabbitListener(queues = RabbitmqConfig.SELECTION)
    @Transactional
    public void consumeMessageFromQueue(SelectedOfferDto selection){
        Offer offer = offerService.getById(selection.getOfferId());
        if (offer.getIsAccepted()) return;
        offerService.acceptOffer(offer.getId());
        ClientInfo clientInfo = service.getByRequestId(offer.getAgentRequest().getRequest().getId());
        if (clientInfo == null){
            clientInfo = service.save(ClientInfo.builder().firstName(selection.getName()).lastName(selection.getSurname())
                    .username(selection.getUsername()).contactInformation(selection.getContactInfo()).build());
        }
        AgentRequest agentRequest = agentRequestService.getById(offer.getAgentRequest().getId());
        agentRequest.setClientInfo(clientInfo);
        agentRequestService.save(agentRequest);
    }
}
