package com.example.tourappapi.dao;

import com.example.tourappapi.dao.interfaces.AgentRequestDao;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.repositories.AgentRequestRepository;
import org.springframework.stereotype.Component;

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
}
