package com.example.tourappapi.models;

import com.example.tourappapi.enums.AgentRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    private String language;
    private String tourType;
    private String addressFrom;
    private String addressTo;
    private String travelStartDate;
    private Integer travellerCount;
    private Integer budget;
    private LocalDateTime deadline;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "request",
            cascade = CascadeType.ALL)
    private List<AgentRequest> requestStatuses;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "request",
            cascade = CascadeType.ALL)
    private List<Offer> offers;
}
