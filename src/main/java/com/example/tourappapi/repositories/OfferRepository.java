package com.example.tourappapi.repositories;

import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
    @Query("SELECT l FROM Offer l WHERE l.agent.username =:username")
    List<Offer> findAll(String username);
}
