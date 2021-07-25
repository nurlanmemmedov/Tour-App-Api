package com.example.tourappapi.services;

import com.example.tourappapi.dao.interfaces.AgentDao;
import com.example.tourappapi.models.Agent;
import com.example.tourappapi.services.interfaces.AgentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {

    private AgentDao dao;

    public AgentServiceImpl(AgentDao dao){
        this.dao = dao;
    }

    @Override
    public Agent create(Agent agent) {
        return dao.create(agent);
    }

    @Override
    public Agent getById(Integer id) {
        return dao.getById(id);
    }

    @Override
    public Agent getByUsername(String username) {
        return dao.getByUsername(username);
    }

    @Override
    public void delete(Integer id) {
        dao.delete(id);
    }

    @Override
    public List<Agent> getAll() {
        return dao.getAll();
    }
}
