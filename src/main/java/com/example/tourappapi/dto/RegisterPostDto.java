package com.example.tourappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPostDto {
    @NotNull(message = "Field cannot be null")
    @Email
    private String email;
    @NotNull(message = "Field cannot be null")
    private String username;
    private String voen;
    @NotNull(message = "Field cannot be null")
    @Min(value = 3,message = "Minimum surname length is 3")
    @Max(value = 40,message = "Maximum surname length is 40")
    private String password;

    @NotNull(message = "Field cannot be null")
    @Min(value = 1,message = "Minimum username length is 1")
    @Max(value = 100,message = "Maximum username length is 100")
    private String companyName;

    @NotNull(message = "Field cannot be null")
    @Min(value = 1,message = "Minimum username length is 1")
    @Max(value = 100,message = "Maximum username length is 100")
    private String agentName;
}