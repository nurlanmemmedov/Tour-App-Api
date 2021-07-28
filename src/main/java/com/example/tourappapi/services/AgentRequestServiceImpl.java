package com.example.tourappapi.services;

import com.example.tourappapi.dao.interfaces.AgentRequestDao;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.Agent;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.services.interfaces.AgentRequestService;
import com.example.tourappapi.services.interfaces.AgentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentRequestServiceImpl implements AgentRequestService {

    private AgentRequestDao dao;
    private AgentService agentService;

    public AgentRequestServiceImpl(AgentRequestDao dao, AgentService agentService){
        this.dao = dao;
        this.agentService = agentService;
    }

    @Override
    public AgentRequest save(AgentRequest agentRequest) {
        return dao.save(agentRequest);
    }

    @Override
    public void createByRequest(Request request) {
        List<Agent> agents = agentService.getAll();
        agents.stream().forEach(a -> dao.save(AgentRequest.builder()
                .agent(a).request(request)
                .status(AgentRequestStatus.ACTIVE).build()));
    }

    @Override
    public void changeStatus(Integer id, AgentRequestStatus status, String username) {
        AgentRequest agentRequest = dao.getById(id);
        agentRequest.setStatus(status);
        dao.save(agentRequest);
    }

    @Override
    public AgentRequest getById(Integer id) {
        return dao.getById(id);
    }

    @Override
    public List<AgentRequest> findByStatus(String status, String username) {
        return dao.getAllByStatus(AgentRequestStatus.valueOf(status), username);
    }

    @Override
    public List<AgentRequest> getAll(String username) {
        return dao.getAll(username);
    }
}
