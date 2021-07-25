package com.example.tourappapi.dao.interfaces;

import com.example.tourappapi.models.Request;

import java.util.List;

public interface RequestDao {
    public Request save(Request request);
    List<Request> getOfferedRequests(String username);
}
