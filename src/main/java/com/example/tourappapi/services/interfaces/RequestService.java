package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.models.Request;

import java.util.List;

public interface RequestService {
    Request save(Request request);
    Request getById(Integer id);
    Request getByUuid(String uuid);
    List<Request> getAllExpiredRequests();
    void updateExpiredRequests();
}
