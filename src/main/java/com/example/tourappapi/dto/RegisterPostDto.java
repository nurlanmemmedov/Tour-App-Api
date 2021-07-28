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
    @Min(value = 6, message = "Minimum length is 6")
    @Max(value = 40, message = "Maximum length is 40")
    private String password;

    @NotNull(message = "Field cannot be null")
    @Max(value = 100,message = "Maximum length is 100")
    private String companyName;

    @NotNull(message = "Field cannot be null")
    @Max(value = 100,message = "Maximum length is 100")
    private String agentName;
}