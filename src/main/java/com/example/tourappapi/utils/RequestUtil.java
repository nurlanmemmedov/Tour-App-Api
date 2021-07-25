package com.example.tourappapi.utils;

import com.example.tourappapi.models.Request;

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

    public static boolean validateDeadline(Request request){
        return LocalDateTime.now().isBefore(request.getDeadline());
    }

    public static boolean validateWorkingHours(Request request, Integer workStart, Integer workEnd){
        LocalDateTime start = LocalDateTime.now().withHour(workStart).withMinute(0).withSecond(0);
        LocalDateTime end = LocalDateTime.now().withHour(workEnd).withMinute(0).withSecond(0);
        if (LocalDateTime.now().isBefore(start) || LocalDateTime.now().isAfter(end)){
            return false;
        }
        return true;
    }

}
