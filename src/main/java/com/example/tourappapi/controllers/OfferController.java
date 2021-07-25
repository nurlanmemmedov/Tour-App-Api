package com.example.tourappapi.controllers;

import com.example.tourappapi.dto.UserDto;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.services.interfaces.AgentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1/offers")
@RestController
public class OfferController {
    private AgentRequestService service;

    public OfferController(AgentRequestService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AgentRequest>> getAll(@RequestAttribute("user") UserDto userDto){
        return new ResponseEntity<List<AgentRequest>>(service.getAll(userDto.getUsername()), HttpStatus.OK);
    }
}

