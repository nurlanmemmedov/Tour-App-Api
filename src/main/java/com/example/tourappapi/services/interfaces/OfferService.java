package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.dto.OfferPostDto;
import com.example.tourappapi.models.Offer;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface OfferService {
    Offer save(String username, Integer id , OfferPostDto offer) throws JRException, FileNotFoundException;

    void delete(String username, Integer id);

    Offer getById(Integer id);

    List<Offer> getAll(String username);

    Boolean acceptOffer(Integer id);
}
