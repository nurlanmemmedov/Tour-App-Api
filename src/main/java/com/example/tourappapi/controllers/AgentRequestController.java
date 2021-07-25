package com.example.tourappapi.controllers;

import com.example.tourappapi.dto.OfferPostDto;
import com.example.tourappapi.dto.UserDto;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.services.interfaces.AgentRequestService;
import com.example.tourappapi.services.interfaces.OfferService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RequestMapping(value = "/api/v1/requests")
@RestController
public class AgentRequestController {
    private AgentRequestService service;

    private OfferService offerService;

    public AgentRequestController(AgentRequestService service,
                                  OfferService offerService){
        this.service = service;
        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<List<AgentRequest>> getAll(@RequestAttribute("user") UserDto userDto){
        return new ResponseEntity<List<AgentRequest>>(service.getAll(userDto.getUsername()), HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<List<AgentRequest>> getAllByStatus(@RequestAttribute("user") UserDto userDto,
                                                             @RequestParam String status){
        return new ResponseEntity<List<AgentRequest>>(service.findByStatus(status, userDto.getUsername()), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}/change-status")
    public ResponseEntity makeArchived(@RequestAttribute("user") UserDto userDto,
                                       @PathVariable Integer id) {
        service.changeStatus(id, AgentRequestStatus.ARCHIVED,  userDto.getUsername());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/create-offer")
    public ResponseEntity createOffer(@RequestAttribute("user") UserDto userDto,
                                      @PathVariable Integer id,
                                      @RequestBody OfferPostDto offerPostDto) throws JRException, FileNotFoundException {
        offerService.save(userDto.getUsername(), id, offerPostDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
