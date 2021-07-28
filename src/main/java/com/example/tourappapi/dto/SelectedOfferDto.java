package com.example.tourappapi.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class SelectedOfferDto implements Serializable {
    private Integer clientId;
    private String name;
    private String surname;
    private String username;
    private String contactInfo;
    private String uuid;
    private Integer offerId;
}

