package com.example.tourappapi.services;

import com.example.tourappapi.configs.RabbitmqConfig;
import com.example.tourappapi.dto.OfferDto;
import com.example.tourappapi.models.Offer;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.services.interfaces.RabbitmqService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * this class implements RabbitmqService and used for interact with rabbitmq
 */
@Service
public class RabbitmqServiceImpl implements RabbitmqService {

    @Autowired
    private RabbitTemplate template;

    /**
     * {@inheritDoc}
     * @param offer
     */
    @Override
    public void sendToOfferQueue(OfferDto offer) {
        template.convertAndSend(RabbitmqConfig.OFFERQUEUE, offer);
    }

    /**
     * {@inheritDoc}
     * @param uuid
     */
    @Override
    public void sendToExpiredQueue(String uuid) {
        template.convertAndSend(RabbitmqConfig.EXPIREDQUEUE, uuid);
    }
}
