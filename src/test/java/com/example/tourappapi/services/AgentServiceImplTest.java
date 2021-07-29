package com.example.tourappapi.services;

import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.Agent;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.repositories.AgentRepository;
import com.example.tourappapi.repositories.AgentRequestRepository;
import com.example.tourappapi.repositories.RequestRepository;
import com.example.tourappapi.services.interfaces.AgentRequestService;
import com.example.tourappapi.services.interfaces.AgentService;
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
public class AgentServiceImplTest {
    @Autowired
    private AgentService agentService;

    @Autowired
    private AgentRepository agentRepository;


//    @Test
//    @Order(1)
//    @Transactional
//    @DisplayName("AgentService -> get By Id")
//    void getAgentById() {
//        Assertions.assertEquals(agentService.getById(1), agentRepository.getById(1));
//    }

    @Test
    @Order(2)
    @Transactional
    @DisplayName("AgentService -> get By Email")
    void getAgentByEmail() {
        Assertions.assertEquals(agentService.getByEmail("agency@agent.com"), agentRepository.getAgentByEmail("agency@agent.com"));
    }

    @Test
    @Order(3)
    @Transactional
    @DisplayName("AgentService -> get By Username")
    void getAgentByUsername() {
        Assertions.assertEquals(agentService.getByUsername("testm"), agentRepository.getAgentByUsername("testm"));
    }

    @Test
    @Order(4)
    @Transactional
    @DisplayName("AgentService -> get All Agents")
    void getAll() {
        Assertions.assertEquals(agentService.getAll().size(), 3);
    }

    @Test
    @Order(5)
    @DisplayName("AgentService -> Check if exists")
    void checkIfExists() {
        Assertions.assertFalse(agentService.checkIfExists(Agent.builder()
                .username("aaaa").email("aaa@agent.com").voen("0000").build()));
    }

    @Test
    @Order(6)
    @Transactional
    @DisplayName("AgentService -> Save")
    void save() {
        agentService.save(Agent.builder().agentName("AGE1").companyName("COMP1").username("usrnm12")
        .email("email1@email1.com").build());
        Assertions.assertEquals(agentRepository.findAll().size(), 4);
    }

//    @Test
//    @Order(7)
//    @Transactional
//    @DisplayName("AgentService -> Delete")
//    void delete() {
//        agentService.delete(3);
//        Assertions.assertEquals(agentRepository.findAll().size(), 2);
//    }


    @BeforeAll
    public void init() {
        agentRepository.saveAndFlush(Agent.builder().username("nurlanm").agentName("AGENCYOne").voen("1234567").email("agency@agent.com").companyName("CompanyOne").build());
        agentRepository.saveAndFlush(Agent.builder().username("testm").agentName("AGENCYTwo").voen("7654321").email("agency2@agent.com").companyName("CompanyTwo").build());
        agentRepository.saveAndFlush(Agent.builder().username("username").agentName("AGENCYThree").voen("27353").email("agency3@agent.com").companyName("CompanyThree").build());
    }


    @AfterAll
    public void clean() {
        agentRepository.deleteAll();
    }

}
