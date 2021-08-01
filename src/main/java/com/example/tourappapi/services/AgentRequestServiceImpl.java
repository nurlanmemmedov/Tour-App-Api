package com.example.tourappapi.services;

import com.example.tourappapi.dao.interfaces.AgentRequestDao;
import com.example.tourappapi.dto.AgentRequestDto;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.exceptions.UserRequestNotFoundException;
import com.example.tourappapi.models.Agent;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.services.interfaces.AgentRequestService;
import com.example.tourappapi.services.interfaces.AgentService;
import com.example.tourappapi.utils.modelmapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentRequestServiceImpl implements AgentRequestService {

    private AgentRequestDao dao;
    private AgentService agentService;
    private Mapper mapper;

    public AgentRequestServiceImpl(AgentRequestDao dao, AgentService agentService, Mapper mapper){
        this.dao = dao;
        this.agentService = agentService;
        this.mapper = mapper;
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
                .isArchived(false)
                .status(AgentRequestStatus.NEWREQUEST).build()));
    }

    @Override
    public boolean toggleArchived(Integer id, String username) {
        AgentRequest agentRequest = getByIdAndUsername(id, username);
        if (agentRequest.getIsArchived() == null){
            agentRequest.setIsArchived(true);
            dao.save(agentRequest);
            return agentRequest.getIsArchived();
        }
        agentRequest.setIsArchived(!agentRequest.getIsArchived());
        dao.save(agentRequest);
        return agentRequest.getIsArchived();
    }


    @Override
    public AgentRequest getById(Integer id) {
        return dao.getById(id);
    }

    @Override
    public AgentRequest getByIdAndUsername(Integer id, String username){
        AgentRequest agentRequest = dao.getByIdAndUsername(id, username);
        if (agentRequest == null) throw new UserRequestNotFoundException();
        return agentRequest;
    }

    @Override
    public List<AgentRequestDto> findByStatus(String status, String username) {
        return dao.getAllByStatus(AgentRequestStatus.valueOf(status), username)
                .stream().map(a -> mapper.convertOneDim(a, AgentRequestDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AgentRequestDto> getArchivedRequests(String username) {
        return dao.getArchivedRequests(username).stream()
                .map(a -> mapper.convertOneDim(a, AgentRequestDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AgentRequestDto> getAll(String username) {
        return dao.getAll(username).stream()
                .map(a -> mapper.convertOneDim(a, AgentRequestDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<AgentRequest> getAllByRequestId(Integer id) {
        return dao.getAllByRequestId(id);
    }

    @Override
    public void expireAgentRequests(Integer requestId) {
        dao.expireAgentRequests(requestId);
    }
}
