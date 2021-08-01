package com.example.tourappapi.dao;

import com.example.tourappapi.dao.interfaces.AgentRequestDao;
import com.example.tourappapi.dto.AgentRequestDto;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.repositories.AgentRequestRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgentRequestDaoImpl implements AgentRequestDao {

    private AgentRequestRepository repository;

    public AgentRequestDaoImpl(AgentRequestRepository repository){
        this.repository = repository;
    }

    @Override
    public AgentRequest save(AgentRequest request) {
        return repository.save(request);
    }

    @Override
    public void delete(String username, Integer id) {
        AgentRequest agentRequest = getByIdAndUsername(id, username);
         repository.deleteById(id);
    }

    @Override
    public AgentRequest getByIdAndUsername(Integer id, String username) {
        return repository.getById(id, username);
    }

    @Override
    public List<AgentRequest> getArchivedRequests(String username) {
        return repository.getArchivedRequests(username);
    }

    @Override
    public AgentRequest getById(Integer id) {
        return repository.getById(id);
    }

    @Override
    public List<AgentRequest> getAllByStatus(AgentRequestStatus status, String username) {
        return repository.findAllByStatus(status, username);
    }

    @Override
    public List<AgentRequest> getAll(String username) {
        return repository.findAll(username);
    }

    @Override
    public List<AgentRequest> getAllByRequestId(Integer id) {
        return repository.getAllByRequest(id);
    }

    @Override
    public void expireAgentRequests(Integer requestId) {
        repository.expireAgentRequests(requestId);
    }
}
