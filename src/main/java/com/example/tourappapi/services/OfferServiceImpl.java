package com.example.tourappapi.services;

import com.example.tourappapi.dao.interfaces.OfferDao;
import com.example.tourappapi.dto.OfferDto;
import com.example.tourappapi.dto.OfferPostDto;
import com.example.tourappapi.models.Agent;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.models.Offer;
import com.example.tourappapi.services.interfaces.*;
import com.example.tourappapi.utils.RequestUtil;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferDao dao;
    private JasperService jasperService;
    private FileService fileService;
    private AgentService agentService;
    private AgentRequestService agentRequestService;
    private RabbitmqService rabbitmqService;

    @Value("${workinghours.start}")
    private Integer start;
    @Value("${workinghours.end}")
    private Integer end;

    public OfferServiceImpl(OfferDao dao,
                            JasperService jasperService,
                            FileService fileService,
                            AgentService agentService,
                            AgentRequestService agentRequestService,
                            RabbitmqService rabbitmqService){
        this.dao = dao;
        this.jasperService = jasperService;
        this.fileService = fileService;
        this.agentService = agentService;
        this.agentRequestService = agentRequestService;
        this.rabbitmqService = rabbitmqService;
    }

    @Override
    public Offer save(String username, Integer id, OfferPostDto offer) throws JRException, FileNotFoundException {
        Agent agent = agentService.getByUsername(username); //TODO throw exception
        AgentRequest agentRequest = agentRequestService.getById(id); //TODO throw exception
//        if (!RequestUtil.validateWorkingHours(agentRequest.getRequest(), start, end)) return null; //TODO throw exception
        if (!RequestUtil.validateDeadline(agentRequest.getRequest())) return null; //TODO throw exception
        File offerFile = jasperService.generateImage(offer);
        String imagePath = fileService.upload(offerFile);
        Offer created = dao.save(Offer.builder().imagePath(imagePath)
                .request(agentRequest.getRequest())
                .agent(agent).build());
        rabbitmqService.sendToOfferQueue(OfferDto.builder()
                .uuid(agentRequest.getRequest().getUuid())
                .agentId(agent.getId()).path(imagePath).build());
        return created;
    }

    @Override
    public void delete(String username, Integer id) {

    }

    @Override
    public Offer getById(String username, Integer id) {
        return null;
    }

    @Override
    public List<Offer> getAll(String username) {
        return dao.getAll(username);
    }
}
