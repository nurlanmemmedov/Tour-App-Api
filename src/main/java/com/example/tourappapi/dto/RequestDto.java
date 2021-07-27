package com.example.tourappapi.dto;

import lombok.Data;

import java.util.Map;

@Data
public class RequestDto {
    private String uuid;
    private Map<String , String> answers;
}
