package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.models.ClientInfo;

/**
 * represents the ClientInfo service throughout the application
 */
public interface ClientInfoService {
    /**
     * saves clientInfo
     * @param clientInfo
     * @return
     */
    ClientInfo save(ClientInfo clientInfo);


    /**
     * gets clientInfo by request id
     * @param requestId
     * @return
     */
    ClientInfo getByRequestId(Integer requestId);
}
