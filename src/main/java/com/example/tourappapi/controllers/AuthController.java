package com.example.tourappapi.controllers;

import com.example.tourappapi.dtos.LoginPostDto;
import com.example.tourappapi.dtos.LoginResponseDto;
import com.example.tourappapi.dtos.RegisterPostDto;
import com.example.tourappapi.dtos.RegisterResponseDto;
import com.example.tourappapi.services.interfaces.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/users")
@RestController
public class AuthController {

    AuthService service;

    public AuthController(AuthService service){
        this.service = service;
    }


    @PostMapping(path = "/create")
    public ResponseEntity<String> createUser(@RequestBody RegisterPostDto userDTO) {
        return new ResponseEntity<>(service.register(userDTO), HttpStatus.CREATED);
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<LoginResponseDto> signin(@RequestBody LoginPostDto loginDto) {
        return new ResponseEntity<>(service.login(loginDto), HttpStatus.OK);
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity confirmUserAccount(@RequestParam("token")String confirmationToken){
        service.confirmAccount(confirmationToken);
        return new ResponseEntity(HttpStatus.OK);
    }

}
