package com.example.tourappapi.controllers;

import com.example.tourappapi.dto.AgentRequestDto;
import com.example.tourappapi.dto.OfferPostDto;
import com.example.tourappapi.dto.UserDto;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.services.interfaces.AgentRequestService;
import com.example.tourappapi.services.interfaces.OfferService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RequestMapping(value = "/api/v1/requests")
@RestController
@Validated
public class AgentRequestController {
    private AgentRequestService service;

    private OfferService offerService;

    public AgentRequestController(AgentRequestService service,
                                  OfferService offerService){
        this.service = service;
        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<List<AgentRequestDto>> getAll(@RequestAttribute("user") UserDto userDto){
        return new ResponseEntity<List<AgentRequestDto>>(service.getAll(userDto.getUsername()), HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<List<AgentRequestDto>> getAllByStatus(@RequestAttribute("user") UserDto userDto,
                                                             @RequestParam String status){
        return new ResponseEntity<List<AgentRequestDto>>(service.findByStatus(status, userDto.getUsername()), HttpStatus.OK);
    }

    @GetMapping("/archived")
    public ResponseEntity<List<AgentRequestDto>> getAllByStatus(@RequestAttribute("user") UserDto userDto){
        return new ResponseEntity<List<AgentRequestDto>>(service.getArchivedRequests(userDto.getUsername()), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}/archive")
    public ResponseEntity<Boolean> makeArchived(@RequestAttribute("user") UserDto userDto,
                                       @PathVariable Integer id) {
        return new ResponseEntity(service.toggleArchived(id, userDto.getUsername()), HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/create-offer")
    public ResponseEntity createOffer(@RequestAttribute("user") UserDto userDto,
                                      @PathVariable Integer id,
                                      @RequestBody @Valid OfferPostDto offerPostDto) throws JRException, IOException {
        offerService.save(userDto.getUsername(), id, offerPostDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
