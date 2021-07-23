package com.example.tourappapi.dao;

import com.example.tourappapi.dao.interfaces.AgentRequestDao;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.repositories.AgentRequestRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgentRequestImpl implements AgentRequestDao {

    private AgentRequestRepository repository;

    public AgentRequestImpl(AgentRequestRepository repository){
        this.repository = repository;
    }

    @Override
    public AgentRequest save(AgentRequest request) {
        return repository.save(request);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
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
}
