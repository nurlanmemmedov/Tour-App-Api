package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.models.Request;

import java.util.List;

public interface RequestService {
    Request save(Request request);
    List<Request> getOfferedRequests(String username);
    void updateExpiredRequests();
}
