package com.example.tourappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OfferPostDto {
    private String description;
    private String places;
    private Integer budget;
    private Date startDate;
    private Date endDate;
    private String note;
}
