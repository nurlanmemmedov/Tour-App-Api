package com.example.tourappapi.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ResetPasswordDto {

    @NotNull(message = "Field cannot be null")
    private String token;

    @NotNull(message = "Field cannot be null")
    private String password;
}
