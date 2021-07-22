package com.example.tourappapi.repositories;

import com.example.tourappapi.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Integer> {
}
