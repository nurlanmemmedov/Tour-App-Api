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
    public List<Request> getOfferedRequests(String username) {
        return dao.getOfferedRequests(username);
    }

    @Override
    public void updateExpiredRequests() {
        dao.updateExpiredRequests();
    }
}
