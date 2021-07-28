package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.dto.OfferDto;
import com.example.tourappapi.models.Offer;
import com.example.tourappapi.models.Request;

/**
 * this interface is used to interact with rabbitmq
 */
public interface RabbitmqService {
    /**
     * pushes offer to offer queue in rabbitmq
     * @param offer
     */
    void sendToOfferQueue(OfferDto offer);

    /**
     * pushes uuid to expired queue in rabbitmq
     * @param uuid
     */
    void sendToExpiredQueue(String uuid);
}
