package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.models.Offer;
import com.example.tourappapi.models.Request;

/**
 * this interface is used to interact with rabbitmq
 */
public interface RabbitmqService {
    /**
     * pushes uuid to stop queue in rabbitmq
     * @param offer
     */
    void sendToOfferQueue(Offer offer);

    void sendToRequestQueue(Request request);
}
