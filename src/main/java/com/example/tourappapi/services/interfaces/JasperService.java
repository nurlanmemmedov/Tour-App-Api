package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.dto.OfferPostDto;
import com.example.tourappapi.models.Offer;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.FileNotFoundException;

public interface JasperService {

    /**
     * generates an image from offer data
     * @param offer
     * @return
     * @throws FileNotFoundException
     * @throws JRException
     */
    File generateImage(Offer offer) throws FileNotFoundException, JRException;
}
