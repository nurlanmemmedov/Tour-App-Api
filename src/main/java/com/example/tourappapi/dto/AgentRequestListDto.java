package com.example.tourappapi.dto;

import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.Agent;
import com.example.tourappapi.models.ClientInfo;
import com.example.tourappapi.models.Offer;
import com.example.tourappapi.models.Request;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentRequestListDto {
    private Integer id;

    private Request request;

    private Integer offerId;

    private ClientInfo clientInfo;

    private AgentRequestStatus status;

    private Boolean isArchived;
}
