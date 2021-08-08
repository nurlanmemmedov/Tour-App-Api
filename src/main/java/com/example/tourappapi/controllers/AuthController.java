package com.example.tourappapi.controllers;

import com.example.tourappapi.dto.*;
import com.example.tourappapi.services.interfaces.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/api/v1/users")
@RestController
@Validated
public class AuthController {

    AuthService service;

    public AuthController(AuthService service){
        this.service = service;
    }

    /**
     * this endpoint is used for agent to register the app
     * @param userDTO
     * @return
     */
    @PostMapping(path = "/register")
    public ResponseEntity<String> createUser(@RequestBody @Valid RegisterPostDto userDTO) throws Exception {
        return new ResponseEntity<>(service.register(userDTO), HttpStatus.CREATED);
    }

    /**
     * this endpoint is used for agent to log in the app
     * @param loginDto
     * @return
     */
    @PostMapping(path = "/signin")
    public ResponseEntity<LoginResponseDto> signin(@RequestBody @Valid LoginPostDto loginDto) {
        return new ResponseEntity<>(service.login(loginDto), HttpStatus.OK);
    }

    /**
     * this endpoint is used for agent to confirm his account
     * @param confirmationToken
     * @return
     */
    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity confirmUserAccount(@RequestParam("token")String confirmationToken){
        service.confirmAccount(confirmationToken);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * this endpoint is used for agent to verify his email if he forget the password
     * @param data
     * @return
     */
    @RequestMapping("forgot-password")
    public ResponseEntity forgotPassword(@RequestBody ForgotPasswordDto data){
        service.forgotPassword(data.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * this endpoint is used for agent to reset his password
     * @param data
     * @return
     */
    @RequestMapping("reset-password")
    public ResponseEntity resetPassword(@RequestBody @Valid ResetPasswordDto data){
        service.resetPassword(data);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * this endpoint is used for agent to change his password
     * @param user
     * @param data
     * @return
     */
    @RequestMapping("change-password")
    public ResponseEntity changePassword(@RequestAttribute UserDto user,
                                         @RequestBody @Valid ChangePasswordDto data){
        service.changePassword(user.getUsername(), data);
        return new ResponseEntity(HttpStatus.OK);
    }
}
