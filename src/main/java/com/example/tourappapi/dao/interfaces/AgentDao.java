package com.example.tourappapi.dao.interfaces;

import com.example.tourappapi.models.Agent;

import java.util.List;

public interface AgentDao {
    Agent create(Agent agent);

    Agent getById(Integer id);

    Agent getByUsername(String username);

    Agent getByEmail(String email);

    Agent getByVoen(String voen);

    void delete(Integer id);

    List<Agent> getAll();
}
