package com.example.listener;

import com.example.entity.Cab;
import com.example.service.CabService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.dtos.cab.events.CabEvent;
import common.dtos.driver.events.UpdateDriverStatusEvent;
import common.dtos.enums.CommonStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CabListener {
    private final CabService cabService;
    private final String ADD_CAB_EVENTS = "add-cab-event";
    private final String UPDATE_DRIVER_EVENT = "update-driver-event";
    private KafkaTemplate<String, UpdateDriverStatusEvent> updateDriverStatusEventKafkaTemplate;

    public CabListener(CabService cabService, KafkaTemplate<String, UpdateDriverStatusEvent> updateDriverStatusEventKafkaTemplate) {
        this.cabService = cabService;
        this.updateDriverStatusEventKafkaTemplate = updateDriverStatusEventKafkaTemplate;
    }

    @KafkaListener(topics = ADD_CAB_EVENTS, groupId = "driver-group")
    public void addCabEventDetails(String event) throws JsonProcessingException {
        CabEvent cabEvent = new ObjectMapper().readValue(event, CabEvent.class);
        System.out.println(cabEvent);
        boolean cabExists = cabService.existsByRegistrationNumberAndCabStatus(cabEvent.getRegistrationNumber(), CommonStatus.SUCCESS);
        CommonStatus commonStatus = cabExists ? CommonStatus.FAILED : CommonStatus.SUCCESS;
        saveCabDetailAndUpdateDriverEvent(cabEvent, commonStatus);
    }

    private void saveCabDetailAndUpdateDriverEvent(CabEvent cabEvent, CommonStatus commonStatus) {
        Cab cab = saveCabDetail(cabEvent, commonStatus);
        updateDriverEvent(cab,commonStatus);
    }

    private void updateDriverEvent(Cab cab, CommonStatus driverStatus) {
        UpdateDriverStatusEvent statusEvent = UpdateDriverStatusEvent.builder()
                .driverId(cab.getDriverId())
                .driverStatus(driverStatus)
                .build();
        updateDriverStatusEventKafkaTemplate.send(UPDATE_DRIVER_EVENT, statusEvent);
    }

    private Cab saveCabDetail(CabEvent cabEvent, CommonStatus cabStatus) {
        return cabService.saveCab(Cab.builder()
                            .driverId(cabEvent.getDriverId())
                            .cabType(cabEvent.getCabType())
                            .registrationNumber(cabEvent.getRegistrationNumber())
                            .cabStatus(cabStatus)
                    .build());
    }
}
