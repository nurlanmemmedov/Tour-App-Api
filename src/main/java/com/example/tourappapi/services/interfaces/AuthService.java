package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.dto.LoginPostDto;
import com.example.tourappapi.dto.LoginResponseDto;
import com.example.tourappapi.dto.RegisterPostDto;

public interface AuthService {
    /**
     * Register new user via keycloak
     * @param user
     * @return
     */
    String register(RegisterPostDto user);

    /**
     * confirms account
     * @param confirmationToken
     * @return
     */
    void confirmAccount(String confirmationToken);

    /**
     * Login via keycloak
     * @param user
     * @return
     */
    LoginResponseDto login(LoginPostDto user);
}
