package com.example.tourappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OfferPostDto {

    @NotNull(message = "Field cannot be null")
    private String description;

    @NotNull(message = "Field cannot be null")
    private String places;

    @NotNull(message = "Field cannot be null")
    private Integer budget;

    @NotNull(message = "Field cannot be null")
    private Date startDate;

    @NotNull(message = "Field cannot be null")
    private Date endDate;
    private String note;
}
