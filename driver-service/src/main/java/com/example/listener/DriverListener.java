package com.example.listener;

import com.example.service.DriverService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.dtos.driver.events.UpdateDriverStatusEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DriverListener {
    private final String UPDATE_DRIVER_EVENT = "update-driver-event";
    private final DriverService driverService;

    public DriverListener(DriverService driverService) {
        this.driverService = driverService;
    }

    @KafkaListener(topics = UPDATE_DRIVER_EVENT, groupId = "cab-group")
    public void updateDriverEvent(String event) throws JsonProcessingException {
        UpdateDriverStatusEvent statusEvent = new ObjectMapper().readValue(event, UpdateDriverStatusEvent.class);
        driverService.updateDriverStatus(statusEvent.getDriverId(), statusEvent.getDriverStatus());
    }

}
