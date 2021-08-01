package com.example.tourappapi.repositories;

import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.AgentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AgentRequestRepository extends JpaRepository<AgentRequest , Integer> {

    @Query("SELECT l FROM AgentRequest l WHERE l.status =:status AND l.agent.username = :username")
    List<AgentRequest> findAllByStatus(AgentRequestStatus status, String username);

    @Query("SELECT l FROM AgentRequest l WHERE l.agent.username =:username")
    List<AgentRequest> findAll(String username);

    @Query("SELECT a FROM AgentRequest a WHERE  a.id =:id AND a.agent.username =:username")
    AgentRequest getById(Integer id, String username);

    @Query("SELECT a FROM AgentRequest a WHERE a.request.id =:id")
    List<AgentRequest> getAllByRequest(Integer id);

    @Query("SELECT a FROM AgentRequest a WHERE a.agent.username =:username AND a.isArchived = TRUE")
    List<AgentRequest> getArchivedRequests(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE agentrequests SET status = 3, is_archived = TRUE where status = 0 AND request_id =:id", nativeQuery = true)
    void expireAgentRequests(Integer id);
}
