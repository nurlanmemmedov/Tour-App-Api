package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.models.Agent;

import java.util.List;

public interface AgentService {
    Agent create(Agent agent);

    Agent getById(Integer id);

    Agent getByUsername(String username);

    void delete(Integer id);

    List<Agent> getAll();
}
