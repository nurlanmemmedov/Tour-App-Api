package com.example.tourappapi.repositories;

import com.example.tourappapi.models.ClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientInfoRepository extends JpaRepository<ClientInfo, Integer> {
}
