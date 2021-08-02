package com.example.tourappapi.services;

import com.example.tourappapi.dao.interfaces.ClientInfoDao;
import com.example.tourappapi.models.ClientInfo;
import com.example.tourappapi.services.interfaces.ClientInfoService;
import org.springframework.stereotype.Service;

@Service
public class ClientInfoServiceImpl implements ClientInfoService {

    private ClientInfoDao dao;

    public ClientInfoServiceImpl(ClientInfoDao dao){
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     * @param clientInfo
     * @return
     */
    @Override
    public ClientInfo save(ClientInfo clientInfo) {
        return dao.save(clientInfo);
    }
}
