package com.example.service;

import com.example.entity.Driver;
import com.example.repository.DriverRepository;
import common.dtos.cab.events.CabEvent;
import common.dtos.driver.dto.DriverDto;
import common.dtos.enums.CommonStatus;
import jakarta.transaction.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private KafkaTemplate<String, CabEvent> cabEventKafkaTemplate;
    private final String ADD_CAB_EVENTS = "add-cab-event";

    public DriverService(DriverRepository driverRepository, KafkaTemplate<String, CabEvent> cabEventKafkaTemplate) {
        this.driverRepository = driverRepository;
        this.cabEventKafkaTemplate = cabEventKafkaTemplate;
    }

    public String saveDriver(DriverDto driverDto) {
        Driver driver = driverRepository.save(Driver.builder()
                .driverName(driverDto.getDriverName())
                .driverEmail(driverDto.getDriverEmail())
                .driverLocation(driverDto.getDriverLocation())
                .driverStatus(CommonStatus.PENDING)
                .build());
        CabEvent cabEvent = CabEvent.builder()
                .driverId(driver.getDriverId())
                .cabType(driverDto.getCabDto().getCabType())
                .registrationNumber(driverDto.getCabDto().getRegistrationNumber())
                .build();
        cabEventKafkaTemplate.send(ADD_CAB_EVENTS, cabEvent);
        return "Driver details::"+driver.getDriverId()+" is processed";
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Optional<Driver> getDriverById(UUID driverId) {
        return driverRepository.findById(driverId);
    }

    @Transactional
    public int updateDriverStatus(UUID driverId, CommonStatus driverStatus) {
        return driverRepository.updateDriverStatus(driverId, driverStatus);
    }
}
