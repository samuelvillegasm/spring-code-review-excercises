package com.bootcampW22.EjercicioGlobal.controller;

import com.bootcampW22.EjercicioGlobal.dto.*;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.bootcampW22.EjercicioGlobal.service.IVehicleService;
import com.bootcampW22.EjercicioGlobal.service.VehicleServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    IVehicleService vehicleService;

    public VehicleController(VehicleServiceImpl vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleDto>> getVehicles() {
        return new ResponseEntity<>(vehicleService.searchAllVehicles(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VehicleDto> createVehicle(@Valid @RequestBody VehicleDto vehicleDto) {
        return new ResponseEntity<>(vehicleService.save(vehicleDto), HttpStatus.CREATED);
    }

    @GetMapping("/color/{color}/year/{year}")
    public ResponseEntity<List<VehicleDto>> getByColorAndYear(@PathVariable String color, @PathVariable Integer year) {
        return new ResponseEntity<>(this.vehicleService.searchVehiclesByColorAndYear(color, year), HttpStatus.OK);
    }

    @GetMapping("/brand/{brand}/between/{start_year}/{end_year}")
    public ResponseEntity<List<VehicleDto>> getByBrandAndBetweenStartDateAndEndDate(
            @PathVariable String brand, @PathVariable Integer start_year, @PathVariable Integer end_year) {
        return new ResponseEntity<>(
                this.vehicleService.searchVehiclesByBrandAndBetweenStartDateAndEndDate(brand, start_year, end_year),
                HttpStatus.OK
        );
    }

    @GetMapping("/average_speed/brand/{brand}")
    public ResponseEntity<VehicleAverageSpeedResponseDto> getAverageSpeedByBrand(@PathVariable String brand) {
        return new ResponseEntity<>(
                this.vehicleService.searchAverageSpeedByBrand(brand),
                HttpStatus.OK
        );
    }

    @PostMapping("/batch")
    public ResponseEntity<List<VehicleDto>> createMultiVehicles(
            @Valid @RequestBody CreateMultiVehiclesRequestDto createMultiVehiclesRequestDto) {
        return new ResponseEntity<>(
                this.vehicleService.multiSave(createMultiVehiclesRequestDto),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}/update_speed")
    public ResponseEntity<VehicleDto> updateMaxSpeed(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMaxSpeedVehicleDto updateMaxSpeedVehicleDto
    ) {
        return new ResponseEntity<>(
                this.vehicleService.updateMaxSpeed(id, updateMaxSpeedVehicleDto),
                HttpStatus.OK
        );
    }

    @GetMapping("/fuel_type/{type}")
    public ResponseEntity<List<VehicleDto>> getByFuelType(@PathVariable String type) {
        return new ResponseEntity<>(
                this.vehicleService.searchByFuelType(type),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.vehicleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/transmission/{type}")
    public ResponseEntity<List<VehicleDto>> getByTransmissionType(@PathVariable String type) {
        return new ResponseEntity<>(
                this.vehicleService.searchByTransmissionType(type),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}/update_fuel")
    public ResponseEntity<VehicleDto> updateFuelType(
            @PathVariable Long id,
            @Valid @RequestBody UpdateFuelTypeVehicleDto updateFuelTypeVehicleDto
    ) {
        return new ResponseEntity<>(
                this.vehicleService.updateFuelType(id, updateFuelTypeVehicleDto),
                HttpStatus.OK
        );
    }

    @GetMapping("/average_capacity/brand/{brand}")
    public ResponseEntity<VehicleAverageCapacityResponseDto> getAverageCapacityByBrand(@PathVariable String brand) {
        return new ResponseEntity<>(
                this.vehicleService.searchAverageCapacityByBrand(brand),
                HttpStatus.OK
        );
    }

    @GetMapping("/dimensions")
    public ResponseEntity<List<VehicleDto>> getByDimensions(
            @RequestParam String length,
            @RequestParam String width
    ) {
        return new ResponseEntity<>(
                this.vehicleService.searchByLengthAndWidth(length, width),
                HttpStatus.OK
        );
    }

    @GetMapping("/weight")
    public ResponseEntity<List<VehicleDto>> getByWeight(
            @RequestParam Double min,
            @RequestParam Double max
    ) {
        return new ResponseEntity<>(
                this.vehicleService.searchByWeight(min, max),
                HttpStatus.OK
        );
    }

}
