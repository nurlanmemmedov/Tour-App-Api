package com.example.tourappapi.dao.interfaces;

import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.Agent;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.models.Offer;
import org.springframework.stereotype.Component;

import java.util.List;

public interface OfferDao {
    Offer save(Offer offer);

    void delete(String username, Integer id);

    Offer getById(String username, Integer id);

    List<Offer> getAll(String username);
}
