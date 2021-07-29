package com.example.tourappapi.services;

import com.example.tourappapi.models.Agent;
import com.example.tourappapi.models.ClientInfo;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.repositories.ClientInfoRepository;
import com.example.tourappapi.services.interfaces.ClientInfoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;


@SpringBootTest
@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SuppressWarnings("SpellCheckingInspection")
class ClientInfoServiceImplTest {
    @Autowired
    private ClientInfoService service;

    @Autowired
    private ClientInfoRepository repository;

    @Test
    @Order(1)
    @Transactional
    @DisplayName("AgentService -> Save")
    void save() {
        service.save(ClientInfo.builder().firstName("Test").lastName("Test").username("test123")
                .contactInformation("email@email1.com").build());
        Assertions.assertEquals(repository.findAll().size(), 1);
    }

    @BeforeAll
    public void init() {
    }


    @AfterAll
    public void clean() {
        repository.deleteAll();
    }
}