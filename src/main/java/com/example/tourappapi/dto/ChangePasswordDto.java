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
    @Min(value = 6, message = "Minimum length is 6")
    @Max(value = 40, message = "Maximum length is 40")
    private String newPassword;
}
