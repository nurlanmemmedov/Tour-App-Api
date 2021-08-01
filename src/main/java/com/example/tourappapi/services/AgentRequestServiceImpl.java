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
import com.example.tourappapi.utils.pagination.Paging;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.tourappapi.utils.pagination.PagingUtil.getResult;

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
    public Paging<AgentRequestDto> findByStatus(String status, String username, Integer index, Integer size) {
        Page<AgentRequest> agentRequests = dao.getAllByStatus(AgentRequestStatus.valueOf(status), username, index, size);
        return new Paging<AgentRequestDto>().toBuilder()
                .pageCount((long) agentRequests.getTotalPages())
                .total(agentRequests.getTotalElements())
                .items(getResult(agentRequests).stream()
                        .map(a -> mapper.convertOneDim(a, AgentRequestDto.class))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public Paging<AgentRequestDto> getArchivedRequests(String username, Integer index, Integer size) {
        Page<AgentRequest> agentRequests = dao.getArchivedRequests(username, index, size);
        return new Paging<AgentRequestDto>().toBuilder()
                .pageCount((long) agentRequests.getTotalPages())
                .total(agentRequests.getTotalElements())
                .items(getResult(agentRequests).stream()
                        .map(a -> mapper.convertOneDim(a, AgentRequestDto.class))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public Paging<AgentRequestDto> getAll(String username, Integer index, Integer size) {
        Page<AgentRequest> agentRequests = dao.getAll(username, index, size);
        return new Paging<AgentRequestDto>().toBuilder()
                .pageCount((long) agentRequests.getTotalPages())
                .total(agentRequests.getTotalElements())
                .items(getResult(agentRequests).stream()
                        .map(a -> mapper.convertOneDim(a, AgentRequestDto.class))
                        .collect(Collectors.toList()))
                .build();
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
