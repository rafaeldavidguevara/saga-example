package com.example.repository;

import com.example.entity.Cab;
import common.dtos.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CabRepository extends JpaRepository<Cab, UUID> {
    boolean existsByRegistrationNumberAndCabStatus(String registrationNumber, CommonStatus commonStatus);
}
