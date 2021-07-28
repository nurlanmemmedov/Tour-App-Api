package com.example.tourappapi.dao.interfaces;

import com.example.tourappapi.models.Request;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestDao {
    Request save(Request request);
    Request getById(Integer id);
    Request getByUuid(String uuid);
    List<Request> getAllExpiredRequests();
    void updateExpiredRequests();
}
