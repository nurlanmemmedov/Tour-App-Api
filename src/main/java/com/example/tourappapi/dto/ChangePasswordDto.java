package com.example.tourappapi.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ChangePasswordDto {

    @NotNull(message = "Field cannot be null")
    private String oldPassword;

    @NotNull(message = "Field cannot be null")
    private String newPassword;
}
