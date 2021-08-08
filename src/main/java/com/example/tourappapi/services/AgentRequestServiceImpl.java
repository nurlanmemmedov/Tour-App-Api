package com.example.tourappapi.services;

import com.example.tourappapi.dao.interfaces.AgentRequestDao;
import com.example.tourappapi.dto.AgentRequestDto;
import com.example.tourappapi.dto.AgentRequestListDto;
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

    /**
     * {@inheritDoc}
     * @param agentRequest
     * @return
     */
    @Override
    public AgentRequest save(AgentRequest agentRequest) {
        return dao.save(agentRequest);
    }

    /**
     * {@inheritDoc}
     * @param request
     */
    @Override
    public void createByRequest(Request request) {
        List<Agent> agents = agentService.getAll();
        agents.stream().forEach(a -> dao.save(AgentRequest.builder()
                .agent(a).request(request)
                .isArchived(false)
                .status(AgentRequestStatus.NEWREQUEST).build()));
    }

    /**
     * {@inheritDoc}
     * @param id
     * @param username
     * @return
     */
    @Override
    public boolean toggleArchived(Integer id, String username) {
        AgentRequest agentRequest = dao.getByIdAndUsername(id, username);
        if (agentRequest.getIsArchived() == null){
            agentRequest.setIsArchived(true);
            dao.save(agentRequest);
            return agentRequest.getIsArchived();
        }
        agentRequest.setIsArchived(!agentRequest.getIsArchived());
        dao.save(agentRequest);
        return agentRequest.getIsArchived();
    }

    /**
     * {@inheritDoc}
     * @param id
     * @return
     */
    @Override
    public AgentRequest getById(Integer id) {
        return dao.getById(id);
    }

    /**
     * {@inheritDoc}
     * @param id
     * @param username
     * @return
     */
    @Override
    public AgentRequestDto getByIdAndUsername(Integer id, String username){
        AgentRequest agentRequest = dao.getByIdAndUsername(id, username);
        if (agentRequest == null) throw new UserRequestNotFoundException();
        return mapper.convertOneDim(agentRequest, AgentRequestDto.class);
    }

    /**
     * {@inheritDoc}
     * @param status
     * @param username
     * @param index
     * @param size
     * @return
     */
    @Override
    public Paging<AgentRequestListDto> findByStatus(String status, String username, Integer index, Integer size) {
        AgentRequestStatus enumStatus = null;
        try {
            enumStatus = AgentRequestStatus.valueOf(status);
        }catch (Exception e){
        }
        Page<AgentRequest> agentRequests = dao.getAllByStatus(enumStatus, username, index, size);
        return new Paging<AgentRequestListDto>().toBuilder()
                .pageCount((long) agentRequests.getTotalPages())
                .total(agentRequests.getTotalElements())
                .items(getResult(agentRequests).stream()
                        .map(a -> mapper.convertOneDim(a, AgentRequestListDto.class))
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * {@inheritDoc}
     * @param username
     * @param index
     * @param size
     * @return
     */
    @Override
    public Paging<AgentRequestListDto> getArchivedRequests(String username, Integer index, Integer size) {
        Page<AgentRequest> agentRequests = dao.getArchivedRequests(username, index, size);
        return new Paging<AgentRequestListDto>().toBuilder()
                .pageCount((long) agentRequests.getTotalPages())
                .total(agentRequests.getTotalElements())
                .items(getResult(agentRequests).stream()
                        .map(a -> mapper.convertOneDim(a, AgentRequestListDto.class))
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * {@inheritDoc}
     * @param username
     * @param index
     * @param size
     * @return
     */
    @Override
    public Paging<AgentRequestListDto> getAll(String username, Integer index, Integer size) {
        Page<AgentRequest> agentRequests = dao.getAll(username, index, size);
        return new Paging<AgentRequestListDto>().toBuilder()
                .pageCount((long) agentRequests.getTotalPages())
                .total(agentRequests.getTotalElements())
                .items(getResult(agentRequests).stream()
                        .map(a -> mapper.convertOneDim(a, AgentRequestListDto.class))
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * {@inheritDoc}
     * @param id
     * @return
     */
    @Override
    public List<AgentRequest> getAllByRequestId(Integer id) {
        return dao.getAllByRequestId(id);
    }
}
