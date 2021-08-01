package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.dao.interfaces.AgentRequestDao;
import com.example.tourappapi.dto.AgentRequestDto;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.Agent;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.utils.pagination.Paging;

import java.util.List;

public interface AgentRequestService {
    AgentRequest save(AgentRequest agentRequest);
    void createByRequest(Request request);
    boolean toggleArchived(Integer id, String username);
    AgentRequest getById(Integer id);
    AgentRequest getByIdAndUsername(Integer id, String username);
    Paging<AgentRequestDto> findByStatus(String status, String username, Integer index, Integer size);
    Paging<AgentRequestDto> getArchivedRequests(String username, Integer index, Integer size);
    Paging<AgentRequestDto> getAll(String username, Integer index, Integer size);
    List<AgentRequest> getAllByRequestId(Integer id);
    void expireAgentRequests(Integer requestId);
}
