package com.example.tourappapi.controllers;

import com.example.tourappapi.dto.UserDto;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.services.interfaces.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/api/v1/requests")
@RestController
public class RequestController {

    RequestService service;

    public RequestController(RequestService service){
        this.service = service;
    }

    @GetMapping("/offered")
    public ResponseEntity<List<Request>> getOfferedRequest(@RequestAttribute UserDto user){
        return new ResponseEntity<List<Request>>(service.getOfferedRequests(user.getUsername()), HttpStatus.OK);
    }
}
