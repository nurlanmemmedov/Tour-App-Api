package com.example.tourappapi.dao;

import com.example.tourappapi.dao.interfaces.ClientInfoDao;
import com.example.tourappapi.models.ClientInfo;
import com.example.tourappapi.repositories.ClientInfoRepository;
import org.springframework.stereotype.Component;

@Component
public class ClientInfoDaoImpl implements ClientInfoDao {

    private ClientInfoRepository repository;

    public ClientInfoDaoImpl(ClientInfoRepository repository){
        this.repository = repository;
    }

    @Override
    public ClientInfo save(ClientInfo clientInfo) {
        return repository.save(clientInfo);
    }
}
