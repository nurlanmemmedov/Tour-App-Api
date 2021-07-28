package com.example.tourappapi.services;

import com.example.tourappapi.dao.interfaces.RequestDao;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.services.interfaces.RequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    private RequestDao dao;

    public RequestServiceImpl(RequestDao dao){
        this.dao = dao;
    }

    @Override
    public Request save(Request request){
        return dao.save(request);
    }

    @Override
    public Request getById(Integer id){
        return dao.getById(id);
    }

    @Override
    public void updateExpiredRequests() {
        dao.updateExpiredRequests();
    }

    @Override
    public Request getByUuid(String uuid) {
        return dao.getByUuid(uuid);
    }

    @Override
    public List<Request> getAllExpiredRequests() {
        return dao.getAllExpiredRequests();
    }
}
