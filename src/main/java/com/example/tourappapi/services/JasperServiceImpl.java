package com.example.tourappapi.services;

import com.example.tourappapi.dto.OfferPostDto;
import com.example.tourappapi.models.Offer;
import com.example.tourappapi.services.interfaces.JasperService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@Service
public class JasperServiceImpl implements JasperService {

    String fileName = "offer";

    /**
     * {@inheritDoc}
     * @param offer
     * @return
     * @throws FileNotFoundException
     * @throws JRException
     */
    @Override
    public File generateImage(Offer offer) throws FileNotFoundException, JRException {

        File file = ResourceUtils.getFile("classpath:offers.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        List<OfferPostDto> offers = new ArrayList<>();
        offers.add(OfferPostDto.builder().budget(offer.getBudget())
                .description(offer.getDescription())
                .places(offer.getPlaces())
                .startDate(offer.getStartDate())
                .endDate(offer.getEndDate()).note(offer.getNote()).build());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(offers);
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        try{
            File out = new File(fileName +  "." + "jpg");
            BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, 0,1f);
            ImageIO.write(image, "jpg", out);
            return out;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
