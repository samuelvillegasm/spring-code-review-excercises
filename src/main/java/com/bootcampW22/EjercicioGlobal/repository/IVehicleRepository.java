package com.bootcampW22.EjercicioGlobal.repository;

import com.bootcampW22.EjercicioGlobal.dto.VehicleDto;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IVehicleRepository {
    List<Vehicle> findAll();
    Vehicle save(Vehicle vehicle);
    Optional<Vehicle> findById(Long id);
    List<Vehicle> findByColorAndYear(String color, Integer year);
    List<Vehicle> findByBrandAndBetweenStartDateAndEndDate(String brand, Integer startDate, Integer endDate);
    double findAverageSpeedByBrand(String brand);
    List<Vehicle> findByBrand(String brand);
    List<Vehicle> multiSave(List<Vehicle> vehicles);
    List<Vehicle> findByIds(List<Long> ids);
    Vehicle update(Vehicle vehicle);
    List<Vehicle> findByFuelType(String fuelType);
    void delete(Long id);
    List<Vehicle> findByTransmissionType(String transmissionType);
    double findAverageCapacityByBrand(String brand);

    List<Vehicle> findByDimensions(double minLength, double maxLength, double minWidth, double maxWidth);

    List<Vehicle> findByWeight(double minWeight, double maxWeight);

}
