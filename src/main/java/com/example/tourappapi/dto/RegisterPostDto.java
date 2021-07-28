package com.example.tourappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPostDto {
    @NotNull(message = "Field cannot be null")
    @Email
    private String email;

    @NotNull(message = "Field cannot be null")
    private String username;

    @NotNull(message = "Field cannot be null")
    private String voen;

    @NotNull(message = "Field cannot be null")
    private String password;

    @NotNull(message = "Field cannot be null")
    private String companyName;

    @NotNull(message = "Field cannot be null")
    private String agentName;
}