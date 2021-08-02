package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.models.Request;

import java.util.List;

/**
 * represents the Request service throughout the application
 * is used to make operations with Requests
 */
public interface RequestService {

    /**
     * saves request
     * @param request
     * @return
     */
    Request save(Request request);

    /**
     * gets request by id
     * @param id
     * @return
     */
    Request getById(Integer id);

    /**
     * gets request by uuid
     * @param uuid
     * @return
     */
    Request getByUuid(String uuid);

    /**
     * gets all expired requests
     * @return
     */
    List<Request> getAllExpiredRequests();

    /**
     * updates all expired requests
     */
    void updateExpiredRequests();
}
