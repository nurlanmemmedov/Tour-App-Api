package com.example.tourappapi.repositories;

import com.example.tourappapi.models.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AgentRepository extends JpaRepository<Agent, Integer> {
    Agent getAgentByEmail(String email);
}
