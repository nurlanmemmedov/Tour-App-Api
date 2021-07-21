package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.models.Offer;

/**
 * this interface is used to interact with rabbitmq
 */
public interface RabbitmqService {
    /**
     * pushes uuid to stop queue in rabbitmq
     * @param offer
     */
    void sendToOfferQueue(Offer offer);
}
