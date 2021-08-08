package com.example.tourappapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;

    @Column(name = "answers_json")
    private String answersJson;

    private LocalDateTime deadline;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;
}
