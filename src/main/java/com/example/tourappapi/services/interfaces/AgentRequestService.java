package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.models.Request;

import java.util.List;

public interface AgentRequestService {
    void createByRequest(Request request);
    void changeStatus(Integer id, AgentRequestStatus status, String username);
    List<AgentRequest> findByStatus(String status, String username);
    List<AgentRequest> getAll(String username);
}
