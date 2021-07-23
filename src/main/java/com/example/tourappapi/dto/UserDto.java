package com.example.tourappapi.dto;

import com.example.tourappapi.models.Agent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;

    public UserDto(Agent user){
        this.username = user.getUsername();
    }
}
