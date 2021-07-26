package com.example.tourappapi.dao.interfaces;

import com.example.tourappapi.models.Request;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestDao {
    Request save(Request request);
    List<Request> getOfferedRequests(String username);
    void updateExpiredRequests();
}
