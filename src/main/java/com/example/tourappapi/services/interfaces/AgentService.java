package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.models.Agent;

import java.util.List;

/**
 * represents the Agent service throughout the application
 * is used to make operations with Agents
 */
public interface AgentService {

    /**
     * saves agent
     * @param agent
     * @return
     */
    Agent save(Agent agent);

    /**
     * gets agent by id
     * @param id
     * @return
     */
    Agent getById(Integer id);

    /**
     * gets agent by username
     * @param username
     * @return
     */
    Agent getByUsername(String username);

    /**
     * gets agent by email
     * @param email
     * @return
     */
    Agent getByEmail(String email);

    /**
     * deletes agent by id
     * @param id
     */
    void delete(Integer id);

    /**
     * gets all agents
     * @return
     */
    List<Agent> getAll();

    /**
     * checks if such agent exists
     * @param agent
     * @return
     */
    Boolean checkIfExists(Agent agent);
}
