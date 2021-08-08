package com.example.tourappapi.dao.interfaces;

import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.AgentRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AgentRequestDao {
    AgentRequest save(AgentRequest request);
    void delete(String username, Integer id);
    AgentRequest getByIdAndUsername(Integer id, String username);
    Page<AgentRequest> getArchivedRequests(String username, Integer index, Integer size);
    AgentRequest getById(Integer id);
    Page<AgentRequest> getAllByStatus(AgentRequestStatus status, String username, Integer index, Integer size);
    Page<AgentRequest> getAll(String username, Integer index, Integer size);
    List<AgentRequest> getAllByRequestId(Integer id);
    void expireAgentRequests(Integer requestId);
}
