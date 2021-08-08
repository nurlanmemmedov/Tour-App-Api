package com.example.tourappapi.controllers;

import com.example.tourappapi.dto.UserDto;
import com.example.tourappapi.models.Offer;
import com.example.tourappapi.services.interfaces.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/offers")
@RestController
@Validated
public class OfferController {
    private OfferService service;

    public OfferController(OfferService service){
        this.service = service;
    }

    /**
     * This method is used for the user to get his offer by id
     *  @PathVariable-Offer id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Offer> getById(@RequestAttribute("user") UserDto userDto, @PathVariable Integer id){
        return new ResponseEntity<Offer>(service.get(userDto.getUsername(), id), HttpStatus.OK);
    }

}
