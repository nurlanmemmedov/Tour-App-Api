package com.example.tourappapi.services;

import com.example.tourappapi.models.Request;
import com.example.tourappapi.repositories.RequestRepository;
import com.example.tourappapi.services.interfaces.RequestService;
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
public class RequestServiceImplTest {
    @Autowired
    private RequestService service;

    @Autowired
    private RequestRepository requestRepository;

    @Test
    @Order(1)
    @Transactional
    @DisplayName("RequestService -> Get By uuid")
    void getByUuid() {
        Assertions.assertEquals(service.getByUuid("wsefeuc2-aayc-45c4-kdeb-as91afd7c076").getUuid(), "wsefeuc2-aayc-45c4-kdeb-as91afd7c076");
    }

    @Test
    @Order(2)
    @Transactional
    @DisplayName("RequestService -> Get All expired requests")
    void getAllExpiredRequests() {
        Assertions.assertEquals(service.getAllExpiredRequests().size(), requestRepository.findAllExpiredRequests().size());
    }


    @Test
    @Order(4)
    @Transactional
    @DisplayName("RequestService -> Save")
    void save() {
        service.save(Request.builder().uuid("pp6feuc2-aayc-45c4-adeb-3491afd7c076").deadline(LocalDateTime.now().withHour(23).withMinute(10)).build());
        Assertions.assertEquals(requestRepository.findAll().size(), 6);
    }

    @BeforeAll
    public void init() {
        List<Request> requests = new ArrayList<>();
        requests.add(Request.builder().uuid("1f6feuc2-aayc-45c4-kdeb-as91afd7c076").deadline(LocalDateTime.now().withHour(23).withMinute(14)).build());
        requests.add(Request.builder().uuid("wsefeuc2-aayc-45c4-kdeb-as91afd7c076").deadline(LocalDateTime.now().withHour(23).withMinute(11)).build());
        requests.add(Request.builder().uuid("346feuc2-aayc-45c4-kdeb-as91afd7c076").deadline(LocalDateTime.now().withHour(23).withMinute(15)).build());
        requests.add(Request.builder().uuid("456feuc2-aayc-45c4-kdeb-as91afd7c076").deadline(LocalDateTime.now().withHour(23).withMinute(17)).build());
        requests.add(Request.builder().uuid("236feuc2-aayc-45c4-kdeb-3491afd7c076").deadline(LocalDateTime.now().withHour(23).withMinute(10)).build());
        requestRepository.saveAllAndFlush(requests);
    }


    @AfterAll
    public void clean() {
        requestRepository.deleteAll();
    }
}
