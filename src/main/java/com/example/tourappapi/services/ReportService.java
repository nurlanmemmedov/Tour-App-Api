package com.example.tourappapi.services;

import com.example.tourappapi.dto.OfferDto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@Service
public class ReportService {


    public File exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\user\\Desktop";
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:offers.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        List<OfferDto> offers = new ArrayList<>();
        offers.add(OfferDto.builder().budget(10).description("aa").places("VV").startDate(new Date()).endDate(new Date()).note("bb").build());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(offers);
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\employees.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\employees.pdf");
        }

        String fileName = "offer";
        int pages = jasperPrint.getPages().size();
        for (int i = 0; i < pages; i++) {
            try{
                File out = new File(fileName + "_p" + (i+1) +  "." + "jpg");
                BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, i,1f);
                ImageIO.write(image, "jpg", out); //write image to file
                return out;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
