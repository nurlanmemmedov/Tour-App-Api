package com.example.tourappapi.repositories;

import com.example.tourappapi.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query("SELECT r FROM Request r JOIN Offer o ON o.request = r WHERE o.agent.username=:username GROUP BY r")
    List<Request> getOfferedRequests(String username);
}
