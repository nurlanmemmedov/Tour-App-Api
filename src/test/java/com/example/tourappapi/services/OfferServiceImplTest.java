package com.example.tourappapi.services;

import com.example.tourappapi.dto.OfferPostDto;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.*;
import com.example.tourappapi.repositories.AgentRepository;
import com.example.tourappapi.repositories.AgentRequestRepository;
import com.example.tourappapi.repositories.OfferRepository;
import com.example.tourappapi.repositories.RequestRepository;
import com.example.tourappapi.services.interfaces.OfferService;
import com.google.type.DateTime;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SuppressWarnings("SpellCheckingInspection")
public class OfferServiceImplTest {

    @Autowired
    private OfferService service;

    @Autowired
    private OfferRepository repository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private AgentRequestRepository agentRequestRepository;

    @Autowired
    private AgentRepository agentRepository;

    @MockBean
    private Clock clock;

//    @Test
//    @Order(1)
//    @Transactional
//    @DisplayName("OfferService -> Save")
//    void save() throws JRException, FileNotFoundException {
//        AgentRequest agentRequest = agentRequestRepository.findAll().stream().findFirst().orElse(null);
//        service.save("nurlanm", agentRequest.getId(), OfferPostDto.builder().note("aa")
//                .description("aaa").budget(100).build());
//        Assertions.assertEquals(repository.findAll().size(), 1);
//    }

    @BeforeAll
    public void init() {

        Instant instant = Instant.parse(LocalDateTime.now().withHour(12).toString());
        ZoneId zoneId = ZoneId.systemDefault();
        Clock fixedClock = Clock.fixed(instant, zoneId);

        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());

        List<Agent> agents = new ArrayList<>();
        agents.add(Agent.builder().username("nurlanm").agentName("AGENCYOne").voen("1234567").email("agency@agent.com").companyName("CompanyOne").build());
        agents.add(Agent.builder().username("testm").agentName("AGENCYTwo").voen("7654321").email("agency2@agent.com").companyName("CompanyTwo").build());
        agents.add(Agent.builder().username("username").agentName("AGENCYThree").voen("27353").email("agency3@agent.com").companyName("CompanyThree").build());
        agentRepository.saveAllAndFlush(agents);

        List<Request> requests = new ArrayList<>();
        requests.add(Request.builder().uuid("1f6feuc2-aayc-45c4-kdeb-as91afd7c076").isActive(true).deadline(LocalDateTime.now().withHour(23).withMinute(14)).build());
        requestRepository.saveAllAndFlush(requests);

        List<AgentRequest> agentRequests = new ArrayList<>();
        agentRequests.add(AgentRequest.builder().agent(agents.get(0)).request(requests.get(0)).status(AgentRequestStatus.NEWREQUEST).build());
        agentRequests.add(AgentRequest.builder().agent(agents.get(1)).request(requests.get(0)).status(AgentRequestStatus.ACCEPTED).build());
        agentRequests.add(AgentRequest.builder().agent(agents.get(2)).request(requests.get(0)).status(AgentRequestStatus.OFFERMADE).build());

        List<Offer> offers = new ArrayList<>();
        offers.add(Offer.builder().agentRequest(agentRequests.get(0)).isAccepted(false).build());
        offers.add(Offer.builder().agentRequest(agentRequests.get(1)).isAccepted(false).build());
        offers.add(Offer.builder().agentRequest(agentRequests.get(2)).isAccepted(false).build());

        agentRequestRepository.saveAll(agentRequests);
    }


    @AfterAll
    public void clean() {
        agentRequestRepository.deleteAll();
        agentRepository.deleteAll();
        requestRepository.deleteAll();
        repository.deleteAll();
    }

}