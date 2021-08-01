package com.example.tourappapi.repositories;

import com.example.tourappapi.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE requests SET is_active = FALSE WHERE deadline < now() AND is_active = TRUE", nativeQuery = true)
    void updateExpiredRequests();

    @Query(value = "SELECT * FROM requests WHERE deadline < now() AND is_active = TRUE ", nativeQuery = true)
    List<Request> findAllExpiredRequests();

    Request getByUuid(String uuid);
}
