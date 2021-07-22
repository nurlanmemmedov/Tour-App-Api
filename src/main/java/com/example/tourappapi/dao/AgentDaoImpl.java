package com.example.tourappapi.dao;

import com.example.tourappapi.dao.interfaces.AgentDao;
import com.example.tourappapi.models.Agent;
import com.example.tourappapi.repositories.AgentRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgentDaoImpl implements AgentDao {

    private AgentRepository repository;

    public AgentDaoImpl(AgentRepository repository){
        this.repository = repository;
    }

    @Override
    public Agent create(Agent agent) {
        return repository.save(agent);
    }

    @Override
    public Agent getById(Integer id) {
        return repository.getById(id);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Agent> getAll() {
        return repository.findAll();
    }
}
