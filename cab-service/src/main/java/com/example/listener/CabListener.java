package com.example.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.dtos.cab.events.CabEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CabListener {
    private final String ADD_CAB_EVENTS = "add-cab-event";
    @KafkaListener(topics = ADD_CAB_EVENTS, groupId = "driver-group")
    public void addCabEventDetails(String event) throws JsonProcessingException {
        CabEvent cabEvent = new ObjectMapper().readValue(event, CabEvent.class);
        System.out.println(cabEvent);
    }
}
