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
        int start = 10;
        int end = 18;
        int deadline = 8;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime calculated = LocalDateTime.now();
        Integer left = end - now.getHour();
        if (left >8){
            calculated = now.withHour(start + deadline);
        }else {
            calculated = now.plusDays(1).withHour(start + deadline - left);
        }
        Assertions.assertEquals(RequestUtil.getDeadline(start, end, deadline).getHour(), calculated.getHour());
    }

    @Test
    @DisplayName("Test should pass when deadline is expired")
    void validateDeadline(){
        Request request = Request.builder()
                .deadline(LocalDateTime.now()
                        .minusMinutes(5)).build();
        Assertions.assertFalse(RequestUtil.validateDeadline(request));
    }
}