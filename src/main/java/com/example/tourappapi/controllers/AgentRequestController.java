package com.example.tourappapi.controllers;

import com.example.tourappapi.dto.AgentRequestDto;
import com.example.tourappapi.dto.AgentRequestListDto;
import com.example.tourappapi.dto.OfferPostDto;
import com.example.tourappapi.dto.UserDto;
import com.example.tourappapi.services.interfaces.AgentRequestService;
import com.example.tourappapi.services.interfaces.OfferService;
import com.example.tourappapi.utils.pagination.Paging;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

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

    /**
     * this endpoint is used for agent to get his all agent requests paginated
     * @param size
     * @param index
     * @return
     */
    @GetMapping
    public ResponseEntity<Paging<AgentRequestListDto>> getAll(@RequestAttribute("user") UserDto userDto,
                                                              @RequestParam(required = false, defaultValue = "10") int size,
                                                              @RequestParam(required = false, defaultValue = "1") int index){
        return new ResponseEntity<Paging<AgentRequestListDto>>(service.getAll(userDto.getUsername(), index, size), HttpStatus.OK);
    }

    /**
     * this endpoint is used for agent to get his agent requests by status paginated
     * @param status
     * @param size
     * @param index
     * @return
     */
    @GetMapping("filter")
    public ResponseEntity<Paging<AgentRequestListDto>> getAllByStatus(@RequestAttribute("user") UserDto userDto,
                                                                      @RequestParam String status,
                                                                      @RequestParam(required = false, defaultValue = "10") int size,
                                                                      @RequestParam(required = false, defaultValue = "1") int index){
        return new ResponseEntity<Paging<AgentRequestListDto>>(service.findByStatus(status, userDto.getUsername(), index, size), HttpStatus.OK);
    }

    /**
     * this endpoint is used for agent to get his agent request by id
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<AgentRequestDto> get(@RequestAttribute("user") UserDto userDto,
                                               @PathVariable Integer id) {
        return new ResponseEntity(service.getByIdAndUsername(id, userDto.getUsername()), HttpStatus.OK);
    }


    /**
     * this endpoint is used for agent to get his all archived agent requests
     * @param size
     * @param index
     * @return
     */
    @GetMapping("/archived")
    public ResponseEntity<Paging<AgentRequestListDto>> getAllArchived(@RequestAttribute("user") UserDto userDto,
                                                                      @RequestParam(required = false, defaultValue = "10") int size,
                                                                      @RequestParam(required = false, defaultValue = "1") int index){
        return new ResponseEntity<Paging<AgentRequestListDto>>(service.getArchivedRequests(userDto.getUsername(), index, size), HttpStatus.OK);
    }


    /**
     * this endpoint is used for agent to archive/unarchive his agent request by id
     * @param id
     * @return
     */
    @PutMapping(path = "/{id}/toggle-archive")
    public ResponseEntity<Boolean> toggleArchived(@RequestAttribute("user") UserDto userDto,
                                       @PathVariable Integer id) {
        return new ResponseEntity(service.toggleArchived(id, userDto.getUsername()), HttpStatus.OK);
    }

    /**
     * this endpoint is used for agent to create offer to request
     * @param id
     * @param offerPostDto
     * @return
     * @throws JRException
     * @throws IOException
     */
    @PostMapping(path = "/{id}/create-offer")
    public ResponseEntity createOffer(@RequestAttribute("user") UserDto userDto,
                                      @PathVariable Integer id,
                                      @RequestBody @Valid OfferPostDto offerPostDto) throws JRException, IOException {
        offerService.save(userDto.getUsername(), id, offerPostDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
