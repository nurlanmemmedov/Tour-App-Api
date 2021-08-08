package com.example.tourappapi.services;

import com.example.tourappapi.dao.interfaces.AgentRequestDao;
import com.example.tourappapi.dao.interfaces.RequestDao;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.services.interfaces.AgentRequestService;
import com.example.tourappapi.services.interfaces.RequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    private RequestDao dao;
    private AgentRequestDao agentRequestDao;

    public RequestServiceImpl(RequestDao dao, AgentRequestDao agentRequestDao){
        this.dao = dao;
        this.agentRequestDao = agentRequestDao;
    }

    /**
     * {@inheritDoc}
     * @param request
     * @return
     */
    @Override
    public Request save(Request request){
        return dao.save(request);
    }

    /**
     * {@inheritDoc}
     * @param id
     * @return
     */
    @Override
    public Request getById(Integer id){
        return dao.getById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateExpiredRequests() {
        List<Request> expiredRequests = getAllExpiredRequests();
        expiredRequests.forEach(r -> agentRequestDao.expireAgentRequests(r.getId()));
        dao.updateExpiredRequests();
    }

    /**
     * {@inheritDoc}
     * @param uuid
     * @return
     */
    @Override
    public Request getByUuid(String uuid) {
        return dao.getByUuid(uuid);
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<Request> getAllExpiredRequests() {
        return dao.getAllExpiredRequests();
    }
}
