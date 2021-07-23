package com.example.tourappapi.repositories;

import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.AgentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgentRequestRepository extends JpaRepository<AgentRequest , Integer> {

    @Query("SELECT l FROM AgentRequest l WHERE l.status =:status AND l.agent.username = :username")
    List<AgentRequest> findAllByStatus(AgentRequestStatus status, String username);

    @Query("SELECT l FROM AgentRequest l WHERE l.agent.username =:username")
    List<AgentRequest> findAll(String username);
}
