package com.bootcampW22.EjercicioGlobal.service;

import com.bootcampW22.EjercicioGlobal.dto.*;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;

import java.time.LocalDate;
import java.util.List;

public interface IVehicleService {
    List<VehicleDto> searchAllVehicles();

    VehicleDto save(VehicleDto vehicle);

    List<VehicleDto> searchVehiclesByColorAndYear(String color, Integer year);

    List<VehicleDto> searchVehiclesByBrandAndBetweenStartDateAndEndDate(String brand, Integer startDate, Integer endDate);

    VehicleAverageSpeedResponseDto searchAverageSpeedByBrand(String brand);

    List<VehicleDto> multiSave(CreateMultiVehiclesRequestDto createMultiVehiclesRequestDto);

    VehicleDto updateMaxSpeed(Long id, UpdateMaxSpeedVehicleDto updateMaxSpeedVehicleDto);

    List<VehicleDto> searchByFuelType(String fuelType);

    void delete(Long id);
    List<VehicleDto> searchByTransmissionType(String transmissionType);

    VehicleDto updateFuelType(Long id, UpdateFuelTypeVehicleDto updateFuelTypeVehicleDto);

    VehicleAverageCapacityResponseDto searchAverageCapacityByBrand(String brand);
    List<VehicleDto> searchByLengthAndWidth(String length, String width);
    List<VehicleDto> searchByWeight(Double minWeight, Double maxWeight);
}
