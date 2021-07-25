package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.dto.OfferDto;
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
    void sendToOfferQueue(OfferDto offer);

    void sendToRequestQueue(Request request);
}
