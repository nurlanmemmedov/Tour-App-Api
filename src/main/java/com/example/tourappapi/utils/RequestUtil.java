package com.example.tourappapi.utils;

import com.example.tourappapi.models.Request;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Date;

public class RequestUtil {

    public static LocalDateTime getDeadline(Integer workStart, Integer workEnd, Integer expireTime){
        LocalDateTime end = LocalDateTime.now().withHour(workEnd).withMinute(0).withSecond(0);
        LocalDateTime calc = LocalDateTime.now();
        if (workEnd == 0) workEnd = 24;
        if (calc.isAfter(end)) calc = calc.plusDays(1).withHour(workStart).withMinute(0).withSecond(0);

        while (expireTime >= workEnd - calc.getHour()){
            expireTime -= workEnd - calc.getHour();
            calc = calc.plusDays(1).withHour(workStart);
        }
        return calc.plusHours(expireTime);
    }

    public static boolean validateDeadline(Request request){
        return LocalDateTime.now().isBefore(request.getDeadline());
    }

    public static boolean validateWorkingHours(Integer workStart, Integer workEnd){
        LocalDateTime start = LocalDateTime.now().withHour(workStart).withMinute(0).withSecond(0);
        LocalDateTime end = LocalDateTime.now().withHour(workEnd).withMinute(0).withSecond(0);
        return !(LocalDateTime.now().isBefore(start) || LocalDateTime.now().isAfter(end));
    }

}
