package com.example.tourappapi.controllers;

import com.example.tourappapi.dto.RegisterPostDto;
import com.example.tourappapi.dto.UserDto;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.services.interfaces.AgentRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1/requests")
@RestController
public class AgentRequestController {
    private AgentRequestService service;

    public AgentRequestController(AgentRequestService service){
        this.service = service;
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

}
