package com.example.tourappapi.utils;

import com.example.tourappapi.models.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestUtilTest {
    @Test
    @DisplayName("Test should pass when deadline calculated correctly")
    void getDeadline(){
        int workStart = 10;
        int workEnd = 18;
        int expireTime = 8;
        LocalDateTime end = LocalDateTime.now().withHour(workEnd).withMinute(0).withSecond(0);
        LocalDateTime calc = LocalDateTime.now();
        if (workEnd == 0) workEnd = 24;
        if (calc.isAfter(end)) calc = calc.plusDays(1).withHour(workStart).withMinute(0).withSecond(0);
        while (expireTime >= workEnd - calc.getHour()){
            expireTime -= workEnd - calc.getHour();
            System.out.println(expireTime);
            calc = calc.plusDays(1).withHour(workStart);
        }
        calc = calc.plusHours(expireTime);

        Assertions.assertEquals(RequestUtil.getDeadline(10, 18, 8).getHour(), calc.getHour());
    }

    @Test
    @DisplayName("Validate Deadline -> EXPIRED")
    void validateDeadlineExpired(){
        Request request = Request.builder()
                .deadline(LocalDateTime.now()
                        .minusMinutes(5)).build();
        Assertions.assertFalse(RequestUtil.validateDeadline(request));
    }

    @Test
    @DisplayName("Validate Deadline -> NOT EXPIRED")
    void validateDeadlineNotExpired(){
        Request request = Request.builder()
                .deadline(LocalDateTime.now()
                        .plusHours(5)).build();
        Assertions.assertTrue(RequestUtil.validateDeadline(request));
    }
}