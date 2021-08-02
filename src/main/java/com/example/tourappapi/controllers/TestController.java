package com.example.tourappapi.controllers;

import com.example.tourappapi.dto.AgentRequestDto;
import com.example.tourappapi.dto.UserDto;
import com.example.tourappapi.models.Offer;
import com.example.tourappapi.services.interfaces.JasperService;
import com.example.tourappapi.utils.pagination.Paging;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.Date;

@RequestMapping(value = "/api/v1/test")
@RestController
public class TestController {
    private JasperService jasperService;

    public TestController(JasperService jasperService){
        this.jasperService = jasperService;
    }

    @GetMapping
    public ResponseEntity test() throws JRException, FileNotFoundException {
        return new ResponseEntity<>(jasperService.generateImage(Offer.builder().note("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English")
                .endDate(new Date()).startDate(new Date()).budget(100).places("Baku, Quba, Qecres")
                .description("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English").build()), HttpStatus.OK);
    }
}
