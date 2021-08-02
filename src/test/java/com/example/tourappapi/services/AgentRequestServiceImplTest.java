package com.example.tourappapi.services;

import com.example.tourappapi.dto.OfferPostDto;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.Agent;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.repositories.AgentRepository;
import com.example.tourappapi.repositories.AgentRequestRepository;
import com.example.tourappapi.repositories.RequestRepository;
import com.example.tourappapi.services.interfaces.AgentRequestService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest
@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SuppressWarnings("SpellCheckingInspection")
class AgentRequestServiceImplTest {
    @Autowired
    private AgentRequestService agentRequestService;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private AgentRequestRepository agentRequestRepository;


    @Test
    @Order(1)
    @DisplayName("AgentRequestService -> get AgentRequests -> ACTIVE")
    void getActiveRequests() {
        Assertions.assertEquals(agentRequestService
                .findByStatus("NEWREQUEST", "nurlanm",1, 10).getItems().size(), 2);

    }

    @Test
    @Order(2)
    @DisplayName("AgentRequestService -> get AgentRequests -> OFFERED")
    void getOfferedRequests() {
        Assertions.assertEquals(agentRequestService.
                findByStatus("OFFERMADE", "nurlanm", 1, 10).getItems().size(), 2);

    }

    @Test
    @Order(3)
    @DisplayName("AgentRequestService -> get AgentRequests -> ACCEPTED")
    void getArchievedRequests() {
        Assertions.assertEquals(agentRequestService
                .findByStatus("ACCEPTED", "username",1, 10).getItems().size(), 2);

    }

    @Test
    @Order(4)
    @DisplayName("AgentRequestService -> getAgentRequests -> ALL")
    void getAllRequests() {
        Assertions.assertEquals(agentRequestService
                .getAll("nurlanm",1,10).getItems().size(), 4);

    }

    @Test
    @Order(5)
    @Transactional
    @DisplayName("AgentRequestService -> Get AgentRequest By Id")
    void getById() {
        AgentRequest agentRequest = agentRequestRepository.findAll().stream().findFirst().orElse(null);
        Assertions.assertEquals(agentRequestService.getById(agentRequest.getId()).getRequest().getUuid(), agentRequest.getRequest().getUuid());
    }



    @Test
    @Order(7)
    @Transactional
    @DisplayName("AgentRequestService -> Save")
    void save() {
        agentRequestService.save(AgentRequest.builder().request(requestRepository.getByUuid("1f6feuc2-aayc-45c4-kdeb-as91afd7c076"))
                .agent(agentRepository.getById(1)).status(AgentRequestStatus.NEWREQUEST).build());
        Assertions.assertEquals(agentRequestRepository.findAll().size(), 13);
    }

    @Test
    @Order(8)
    @Transactional
    @DisplayName("Toggle Archived -> True")
    void toggleArchived() {
        AgentRequest request = agentRequestRepository.findAll().stream().findFirst().orElse(null);
        Assertions.assertTrue(agentRequestService.toggleArchived(request.getId(), "nurlanm"));
    }



    @Test
    @Order(9)
    @Transactional
    @DisplayName("AgentRequestService -> Create By Request")
    void createByRequest() {
        agentRequestService.createByRequest(requestRepository.getByUuid("236feuc2-aayc-45c4-kdeb-3491afd7c076"));
        Assertions.assertEquals(agentRequestRepository.findAll().size(), 15);
    }

    @BeforeAll
    public void init() {
        List<Agent> agents = new ArrayList<>();
        agents.add(Agent.builder().username("nurlanm").agentName("AGENCYOne").voen("1234567").email("agency@agent.com").companyName("CompanyOne").build());
        agents.add(Agent.builder().username("testm").agentName("AGENCYTwo").voen("7654321").email("agency2@agent.com").companyName("CompanyTwo").build());
        agents.add(Agent.builder().username("username").agentName("AGENCYThree").voen("27353").email("agency3@agent.com").companyName("CompanyThree").build());
        agentRepository.saveAllAndFlush(agents);

        List<Request> requests = new ArrayList<>();
        requests.add(Request.builder().uuid("1f6feuc2-aayc-45c4-kdeb-as91afd7c076").deadline(LocalDateTime.now().withHour(23).withMinute(14)).build());
        requests.add(Request.builder().uuid("wsefeuc2-aayc-45c4-kdeb-as91afd7c076").deadline(LocalDateTime.now().withHour(23).withMinute(11)).build());
        requests.add(Request.builder().uuid("346feuc2-aayc-45c4-kdeb-as91afd7c076").deadline(LocalDateTime.now().withHour(23).withMinute(15)).build());
        requests.add(Request.builder().uuid("456feuc2-aayc-45c4-kdeb-as91afd7c076").deadline(LocalDateTime.now().withHour(23).withMinute(17)).build());
        requests.add(Request.builder().uuid("236feuc2-aayc-45c4-kdeb-3491afd7c076").deadline(LocalDateTime.now().withHour(23).withMinute(10)).build());
        requestRepository.saveAllAndFlush(requests);

        List<AgentRequest> agentRequests = new ArrayList<>();
        agentRequests.add(AgentRequest.builder().agent(agents.get(0)).request(requests.get(0)).status(AgentRequestStatus.NEWREQUEST).build());
        agentRequests.add(AgentRequest.builder().agent(agents.get(1)).request(requests.get(0)).status(AgentRequestStatus.ACCEPTED).build());
        agentRequests.add(AgentRequest.builder().agent(agents.get(2)).request(requests.get(0)).status(AgentRequestStatus.OFFERMADE).build());
        agentRequests.add(AgentRequest.builder().agent(agents.get(0)).request(requests.get(1)).status(AgentRequestStatus.OFFERMADE).build());
        agentRequests.add(AgentRequest.builder().agent(agents.get(1)).request(requests.get(1)).status(AgentRequestStatus.NEWREQUEST).build());
        agentRequests.add(AgentRequest.builder().agent(agents.get(2)).request(requests.get(1)).status(AgentRequestStatus.ACCEPTED).build());
        agentRequests.add(AgentRequest.builder().agent(agents.get(0)).request(requests.get(2)).status(AgentRequestStatus.OFFERMADE).build());
        agentRequests.add(AgentRequest.builder().agent(agents.get(1)).request(requests.get(2)).status(AgentRequestStatus.NEWREQUEST).build());
        agentRequests.add(AgentRequest.builder().agent(agents.get(2)).request(requests.get(2)).status(AgentRequestStatus.ACCEPTED).build());
        agentRequests.add(AgentRequest.builder().agent(agents.get(0)).request(requests.get(3)).status(AgentRequestStatus.NEWREQUEST).build());
        agentRequests.add(AgentRequest.builder().agent(agents.get(1)).request(requests.get(3)).status(AgentRequestStatus.ACCEPTED).build());
        agentRequests.add(AgentRequest.builder().agent(agents.get(2)).request(requests.get(3)).status(AgentRequestStatus.OFFERMADE).build());
        agentRequestRepository.saveAllAndFlush(agentRequests);
    }


    @AfterAll
    public void clean() {
        agentRequestRepository.deleteAll();
        agentRepository.deleteAll();
        requestRepository.deleteAll();
    }

}