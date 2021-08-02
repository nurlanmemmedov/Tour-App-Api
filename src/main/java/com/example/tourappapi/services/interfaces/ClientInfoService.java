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
}
