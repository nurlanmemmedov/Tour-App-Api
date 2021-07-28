package com.example.tourappapi.dao;

import com.example.tourappapi.dao.interfaces.RequestDao;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.repositories.RequestRepository;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public Request getById(Integer id) {
        return repository.getById(id);
    }

    @Override
    public Request getByUuid(String uuid) {
        return repository.getByUuid(uuid);
    }

    @Override
    public void updateExpiredRequests() {
        repository.updateExpiredRequests();
    }

    @Override
    public List<Request> getAllExpiredRequests(){
        return repository.findAllExpiredRequests();
    }
}
