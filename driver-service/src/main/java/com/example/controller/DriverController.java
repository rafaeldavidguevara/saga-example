package com.example.controller;

import com.example.entity.Driver;
import com.example.service.DriverService;
import common.dtos.driver.dto.DriverDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public Driver saveDriver(@RequestBody Driver driver) {
        return driverService.saveDriver(driver);
    }

    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/{driverId}")
    public Optional<Driver> getDriverById(@PathVariable UUID driverId) {
        return driverService.getDriverById(driverId);
    }

}
