package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.dto.OfferPostDto;
import com.example.tourappapi.models.Offer;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * represents the Offer service throughout the application
 * is used to make operations with Offers
 */
public interface OfferService {

    /**
     * saves offer
     * @param username
     * @param id
     * @param offer
     * @return
     * @throws JRException
     * @throws IOException
     */
    Offer save(String username, Integer id , OfferPostDto offer) throws JRException, IOException;

    /**
     * gets offer by id
     * @param id
     * @return
     */
    Offer getById(Integer id);

    /**
     * gets offer by id and username
     * @param id
     * @param username
     * @return
     */
    Offer get(String username, Integer id);

    /**
     * gets all offers
     * @param username
     * @return
     */
    List<Offer> getAll(String username);

    /**
     * accepts offer as selected
     * @param id
     * @return
     */
    Boolean acceptOffer(Integer id);
}
