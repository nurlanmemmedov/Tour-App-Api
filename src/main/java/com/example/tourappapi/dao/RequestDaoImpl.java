package com.example.tourappapi.dao;

import com.example.tourappapi.dao.interfaces.RequestDao;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.repositories.RequestRepository;
import org.springframework.stereotype.Component;

@Component
public class RequestDaoImpl implements RequestDao {
    private RequestRepository repository;

    public RequestDaoImpl(RequestRepository repository){
        this.repository = repository;
    }

    @Override
    public Request save(Request request){
        return repository.save(request);
    }
}
