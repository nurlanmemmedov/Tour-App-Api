package com.example.tourappapi.dao.interfaces;

import com.example.tourappapi.dto.AgentRequestDto;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.AgentRequest;

import java.util.List;

public interface AgentRequestDao {
    AgentRequest save(AgentRequest request);
    void delete(String username, Integer id);
    AgentRequest getByIdAndUsername(Integer id, String username);
    List<AgentRequest> getArchivedRequests(String username);
    AgentRequest getById(Integer id);
    List<AgentRequest> getAllByStatus(AgentRequestStatus status, String username);
    List<AgentRequest> getAll(String username);
    List<AgentRequest> getAllByRequestId(Integer id);
    void expireAgentRequests(Integer requestId);
}
