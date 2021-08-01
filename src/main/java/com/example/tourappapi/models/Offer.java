package com.example.tourappapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "offer")
    @JsonBackReference
    private AgentRequest agentRequest;

    private String description;

    private String places;

    private Integer budget;

    private Date startDate;

    private Date endDate;

    private String note;

    private Boolean isAccepted;
}
