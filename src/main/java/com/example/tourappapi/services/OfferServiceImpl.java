package com.example.tourappapi.services;

import com.example.tourappapi.dao.interfaces.OfferDao;
import com.example.tourappapi.dto.OfferDto;
import com.example.tourappapi.dto.OfferPostDto;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.exceptions.AlreadyHaveOfferException;
import com.example.tourappapi.exceptions.NotWorkTimeException;
import com.example.tourappapi.exceptions.RequestInactiveException;
import com.example.tourappapi.exceptions.RequestIsArchivedException;
import com.example.tourappapi.models.Agent;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.models.Offer;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.services.interfaces.*;
import com.example.tourappapi.utils.RequestUtil;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferDao dao;
    private JasperService jasperService;
    private AgentService agentService;
    private AgentRequestService agentRequestService;
    private RabbitmqService rabbitmqService;
    private RequestService requestService;

    @Value("${workinghours.start}")
    private Integer start;
    @Value("${workinghours.end}")
    private Integer end;

    public OfferServiceImpl(OfferDao dao,
                            JasperService jasperService,
                            AgentService agentService,
                            AgentRequestService agentRequestService,
                            RabbitmqService rabbitmqService,
                            RequestService requestService){
        this.dao = dao;
        this.jasperService = jasperService;
        this.agentService = agentService;
        this.agentRequestService = agentRequestService;
        this.rabbitmqService = rabbitmqService;
        this.requestService = requestService;
    }

    @Override
    public Offer save(String username, Integer id, OfferPostDto offer) throws JRException, IOException {
        AgentRequest agentRequest = agentRequestService.getByIdAndUsername(id, username);
        if (!RequestUtil.validateWorkingHours(agentRequest.getRequest(), start, end)) throw new NotWorkTimeException();
        if (!agentRequest.getRequest().getIsActive() || agentRequest.getStatus() == AgentRequestStatus.EXPIRED) throw new RequestInactiveException();
        if(agentRequest.getIsArchived()) throw new RequestIsArchivedException();
//        if(agentRequest.getOffer() != null) throw new AlreadyHaveOfferException();
        Offer created = dao.save(Offer.builder().description(offer.getDescription()).places(offer.getPlaces())
                .budget(offer.getBudget()).agentRequest(agentRequest).isAccepted(false)
                .startDate(offer.getStartDate()).note(offer.getNote()).endDate(offer.getEndDate()).build());
        agentRequest.setStatus(AgentRequestStatus.OFFERMADE);
        agentRequest.setOffer(created);
        agentRequestService.save(agentRequest);
        handleFile(created);
        return created;
    }

    @Override
    public Offer getById(Integer id) {
        return dao.getById(id);
    }

    @Override
    public List<Offer> getAll(String username) {
        return dao.getAll(username);
    }

    @Override
    @Transactional
    public Boolean acceptOffer(Integer id) {
        Offer offer = getById(id);
        offer.setIsAccepted(true);
        dao.save(offer);

        AgentRequest agentRequest = offer.getAgentRequest();
        agentRequest.setStatus(AgentRequestStatus.ACCEPTED);
        agentRequestService.save(agentRequest);

        Request request = requestService.getById(offer.getAgentRequest().getRequest().getId());
        request.setIsActive(false);
        requestService.save(request);
        return true;
    }

    public void handleFile(Offer offer) throws JRException, IOException {
        File offerFile = jasperService.generateImage(offer);
        rabbitmqService.sendToOfferQueue(OfferDto.builder()
                .uuid(offer.getAgentRequest().getRequest().getUuid())
                .offerId(offer.getId()).image(FileUtils.readFileToByteArray(offerFile)).build());
    }
}
