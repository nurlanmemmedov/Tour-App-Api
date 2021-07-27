package com.example.tourappapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "confirmation_tokens")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationToken;

    private LocalDateTime createdDate;

    @OneToOne(targetEntity = Agent.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "agent_id")
    private Agent agent;

    public ConfirmationToken(Agent agent) {
        this.agent = agent;
        createdDate = LocalDateTime.now();
        confirmationToken = UUID.randomUUID().toString();
    }

}
