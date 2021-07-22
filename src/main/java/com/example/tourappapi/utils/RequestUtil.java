package com.example.tourappapi.utils;

import java.time.LocalDateTime;
import java.util.Date;

public class RequestUtil {
    public static LocalDateTime getDeadline(Integer workStart, Integer workEnd, Integer deadline){
        LocalDateTime now = LocalDateTime.now();
        Integer leftDays = workEnd - now.getHour();
        if (leftDays >8){
            return now.withHour(workStart + deadline);
        }else {
            return now.plusDays(1).withHour(workStart + deadline - leftDays);
        }
    }
}
