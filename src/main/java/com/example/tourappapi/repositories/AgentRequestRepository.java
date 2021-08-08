package com.example.tourappapi.repositories;

import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.AgentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AgentRequestRepository extends JpaRepository<AgentRequest , Integer> {

    @Query("SELECT l FROM AgentRequest l WHERE l.status =:status AND l.agent.username = :username ORDER BY l.id DESC")
    Page<AgentRequest> findAllByStatus(AgentRequestStatus status, String username, Pageable pageable);

    @Query("SELECT l FROM AgentRequest l WHERE l.agent.username =:username ORDER BY l.id DESC")
    Page<AgentRequest> findAll(String username, Pageable pageable);

    @Query("SELECT a FROM AgentRequest a WHERE  a.id =:id AND a.agent.username =:username")
    AgentRequest getById(Integer id, String username);

    AgentRequest getById(Integer id);

    @Query("SELECT a FROM AgentRequest a WHERE a.request.id =:id ORDER BY a.id DESC")
    List<AgentRequest> getAllByRequest(Integer id);

    @Query("SELECT a FROM AgentRequest a WHERE a.agent.username =:username AND a.isArchived = TRUE ORDER BY a.id DESC")
    Page<AgentRequest> getArchivedRequests(String username, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE agentrequests SET status = 3, is_archived = TRUE where status = 0 AND request_id =:id", nativeQuery = true)
    void expireAgentRequests(Integer id);
}
