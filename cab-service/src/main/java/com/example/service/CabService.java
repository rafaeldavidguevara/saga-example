package com.example.service;

import com.example.entity.Cab;
import com.example.repository.CabRepository;
import common.dtos.enums.CommonStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CabService {
    private final CabRepository cabRepository;

    public CabService(CabRepository cabRepository) {
        this.cabRepository = cabRepository;
    }

    public List<Cab> getAllCabs() {
        return cabRepository.findAll();
    }

    public Optional<Cab> getCabById(UUID id) {
        return cabRepository.findById(id);
    }

    public Cab saveCab(Cab cab) {
        return cabRepository.save(cab);
    }

    public void deleteCab(UUID id) {
        cabRepository.deleteById(id);
    }

    public boolean existsByRegistrationNumberAndCabStatus(String registrationNumber, CommonStatus commonStatus) {
        return cabRepository.existsByRegistrationNumberAndCabStatus(registrationNumber, commonStatus);
    }
}
