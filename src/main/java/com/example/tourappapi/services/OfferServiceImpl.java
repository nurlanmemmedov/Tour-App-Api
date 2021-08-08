package com.example.tourappapi.services;

import com.example.tourappapi.dao.interfaces.AgentRequestDao;
import com.example.tourappapi.dao.interfaces.OfferDao;
import com.example.tourappapi.dto.OfferDto;
import com.example.tourappapi.dto.OfferPostDto;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.exceptions.*;
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
    private AgentRequestDao agentRequestDao;
    private RabbitmqService rabbitmqService;
    private RequestService requestService;

    @Value("${workinghours.start}")
    private Integer start;
    @Value("${workinghours.end}")
    private Integer end;

    public OfferServiceImpl(OfferDao dao,
                            JasperService jasperService,
                            AgentService agentService,
                            AgentRequestDao agentRequestDao,
                            RabbitmqService rabbitmqService,
                            RequestService requestService){
        this.dao = dao;
        this.jasperService = jasperService;
        this.agentService = agentService;
        this.agentRequestDao = agentRequestDao;
        this.rabbitmqService = rabbitmqService;
        this.requestService = requestService;
    }

    /**
     * {@inheritDoc}
     * @param username
     * @param id
     * @param offer
     * @return
     * @throws JRException
     * @throws IOException
     */
    @Override
    public Offer save(String username, Integer id, OfferPostDto offer) throws JRException, IOException {
        AgentRequest agentRequest = agentRequestDao.getByIdAndUsername(id, username);
        if (!RequestUtil.validateWorkingHours(start, end)) throw new NotWorkTimeException();
        if (!agentRequest.getRequest().getIsActive() || agentRequest.getStatus() == AgentRequestStatus.EXPIRED) throw new RequestInactiveException();
        if(agentRequest.getIsArchived()) throw new RequestIsArchivedException();
        if(agentRequest.getOffer() != null) throw new AlreadyHaveOfferException();
        Offer created = dao.save(Offer.builder().description(offer.getDescription()).places(offer.getPlaces())
                .budget(offer.getBudget()).agentRequest(agentRequest).isAccepted(false)
                .startDate(offer.getStartDate()).note(offer.getNote()).endDate(offer.getEndDate()).build());
        agentRequest.setStatus(AgentRequestStatus.OFFERMADE);
        agentRequest.setOffer(created);
        agentRequestDao.save(agentRequest);
        handleFile(created);
        return created;
    }

    /**
     * {@inheritDoc}
     * @param id
     * @return
     */
    @Override
    public Offer getById(Integer id) {
        return dao.getById(id);
    }

    @Override
    public Offer get(String username, Integer id) {
        Offer offer = dao.getByIdAndUsername(username, id);
        if (offer == null) throw new OfferNotFoundException();
        return offer;
    }

    /**
     * {@inheritDoc}
     * @param username
     * @return
     */
    @Override
    public List<Offer> getAll(String username) {
        return dao.getAll(username);
    }

    /**
     * {@inheritDoc}
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean acceptOffer(Integer id) {
        Offer offer = getById(id);
        offer.setIsAccepted(true);
        dao.save(offer);

        AgentRequest agentRequest = offer.getAgentRequest();
        agentRequest.setStatus(AgentRequestStatus.ACCEPTED);
        agentRequestDao.save(agentRequest);

        return true;
    }

    public void handleFile(Offer offer) throws JRException, IOException {
        File offerFile = jasperService.generateImage(offer);
        rabbitmqService.sendToOfferQueue(OfferDto.builder()
                .uuid(offer.getAgentRequest().getRequest().getUuid())
                .offerId(offer.getId()).image(FileUtils.readFileToByteArray(offerFile)).build());
    }
}
