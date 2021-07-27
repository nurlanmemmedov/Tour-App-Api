package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.dto.*;

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

    /**
     * sends email to confirm account
     * @param email
     */
    void forgotPassword(String email);

    /**
     * resets the password
     * @param dto
     */
    void resetPassword(ResetPasswordDto dto);

    /**
     * changes password
     * @param dto
     */
    void changePassword(String username, ChangePasswordDto dto);
}
