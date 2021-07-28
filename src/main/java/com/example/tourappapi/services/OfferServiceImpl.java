package com.example.tourappapi.services;

import com.example.tourappapi.dao.interfaces.OfferDao;
import com.example.tourappapi.dto.OfferDto;
import com.example.tourappapi.dto.OfferPostDto;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.exceptions.NotWorkTimeException;
import com.example.tourappapi.exceptions.RequestInactiveException;
import com.example.tourappapi.models.Agent;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.models.Offer;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.services.interfaces.*;
import com.example.tourappapi.utils.RequestUtil;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferDao dao;
    private JasperService jasperService;
    private FileService fileService;
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
                            FileService fileService,
                            AgentService agentService,
                            AgentRequestService agentRequestService,
                            RabbitmqService rabbitmqService,
                            RequestService requestService){
        this.dao = dao;
        this.jasperService = jasperService;
        this.fileService = fileService;
        this.agentService = agentService;
        this.agentRequestService = agentRequestService;
        this.rabbitmqService = rabbitmqService;
        this.requestService = requestService;
    }

    @Override
    public Offer save(String username, Integer id, OfferPostDto offer) throws JRException, FileNotFoundException {
        Agent agent = agentService.getByUsername(username);
        AgentRequest agentRequest = agentRequestService.getById(id);
        if (!RequestUtil.validateWorkingHours(agentRequest.getRequest(), start, end)) throw new NotWorkTimeException();
        if (!agentRequest.getRequest().getIsActive()) throw new RequestInactiveException();
        File offerFile = jasperService.generateImage(offer);
        String imagePath = fileService.upload(offerFile);
        Offer created = dao.save(Offer.builder().imagePath(imagePath)
                .agentRequest(agentRequest).isAccepted(false).build());
        agentRequest.setStatus(AgentRequestStatus.OFFERED);
        agentRequestService.save(agentRequest);
        rabbitmqService.sendToOfferQueue(OfferDto.builder()
                .uuid(agentRequest.getRequest().getUuid())
                .offerId(created.getId()).path(imagePath).build());
        return created;
    }

    @Override
    public void delete(String username, Integer id) {

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

        Request request = requestService.getById(offer.getAgentRequest().getRequest().getId());
        request.setIsActive(false);
        requestService.save(request);
        return true;
    }
}
