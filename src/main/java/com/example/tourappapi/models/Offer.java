package com.example.tourappapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    private Request request;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "agent_id", referencedColumnName = "id")
    private Agent agent;

    private String imagePath;

}
