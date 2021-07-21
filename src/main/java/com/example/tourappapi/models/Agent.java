package com.example.tourappapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "agents")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String voen;
    @Column(name = "agent_name")
    private String agentName;
    @Column(name = "company_name")
    private String companyName;
    private String password;
    @Column(name = "is_active")
    private Boolean isActive;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "request",
            cascade = CascadeType.ALL)
    private List<AgentRequest> requestStatuses;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "request",
            cascade = CascadeType.ALL)
    private List<Offer> offers;
}
