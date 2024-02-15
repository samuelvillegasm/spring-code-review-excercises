package com.bootcampW22.EjercicioGlobal.repository;

import com.bootcampW22.EjercicioGlobal.dto.VehicleAverageSpeedResponseDto;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class VehicleRepositoryImpl implements IVehicleRepository {

    private List<Vehicle> listOfVehicles = new ArrayList<>();

    public VehicleRepositoryImpl() throws IOException {
        loadDataBase();
    }

    @Override
    public List<Vehicle> findAll() {
        return listOfVehicles;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        this.listOfVehicles.add(vehicle);
        return vehicle;
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return this.listOfVehicles.stream().filter((v) -> v.getId().longValue() == id.longValue()).findFirst();
    }

    @Override
    public List<Vehicle> findByColorAndYear(String color, Integer year) {
        return this.listOfVehicles.stream()
                .filter(
                        (v) -> v.getColor().equals(color) && v.getYear().equals(year)
                )
                .toList();
    }

    @Override
    public List<Vehicle> findByBrandAndBetweenStartDateAndEndDate(String brand, Integer starDate, Integer endDate) {
        return this.listOfVehicles.stream()
                .filter(
                        (v) ->
                                v.getBrand().equals(brand) &&
                                        v.getYear() >= starDate &&
                                        v.getYear() <= endDate
                ).toList();
    }

    @Override
    public double findAverageSpeedByBrand(String brand) {
        return this.listOfVehicles.stream()
                .filter((v) -> v.getBrand().equals(brand))
                .mapToDouble(Vehicle::getMax_speed)
                .average().orElse(0.0);
    }

    @Override
    public List<Vehicle> findByBrand(String brand) {
        return this.listOfVehicles.stream()
                .filter(
                        (v) -> v.getBrand().equals(brand)
                ).toList();
    }

    @Override
    public List<Vehicle> multiSave(List<Vehicle> vehicles) {
        this.listOfVehicles.addAll(vehicles);
        return vehicles;
    }

    @Override
    public List<Vehicle> findByIds(List<Long> ids) {
        return this.listOfVehicles.stream()
                .filter((v) -> ids.contains(v.getId()))
                .toList();
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        Optional<Vehicle> vehicleBeforeUpdate = this.listOfVehicles.stream().filter((v) -> v.getId().equals(vehicle.getId())).findFirst();
        if (vehicleBeforeUpdate.isPresent()) {
            Vehicle vehicleUpdated = vehicleBeforeUpdate.get();
            if (vehicle.getMax_speed() != null)
                vehicleUpdated.setMax_speed(vehicle.getMax_speed());
            if (vehicle.getFuel_type() != null)
                vehicleUpdated.setFuel_type(vehicle.getFuel_type());
            return vehicleUpdated;
        } else
            return vehicle;
    }

    @Override
    public List<Vehicle> findByFuelType(String fuelType) {
        return this.listOfVehicles.stream()
                .filter((v) -> v.getFuel_type().equals(fuelType))
                .toList();
    }

    @Override
    public void delete(Long id) {
        Optional<Vehicle> vehicle = this.listOfVehicles.stream()
                .filter((v) -> v.getId().equals(id))
                .findFirst();
        vehicle.ifPresent(value -> this.listOfVehicles.remove(value));
    }

    @Override
    public List<Vehicle> findByTransmissionType(String transmissionType) {
        return this.listOfVehicles.stream()
                .filter((v) -> v.getTransmission().equals(transmissionType))
                .toList();
    }

    @Override
    public double findAverageCapacityByBrand(String brand) {
        return this.listOfVehicles.stream()
                .mapToDouble(Vehicle::getPassengers)
                .average().orElse(0.0);
    }

    @Override
    public List<Vehicle> findByDimensions(double minLength, double maxLength, double minWidth,
                                          double maxWidth) {
        return this.listOfVehicles.stream()
                .filter(
                        (v) ->
                                v.getLength() >= minLength &&
                                        v.getLength() <= maxLength &&
                                        v.getWidth() >= minWidth &&
                                        v.getWidth() <= maxWidth
                )
                .toList();
    }

    @Override
    public List<Vehicle> findByWeight(double minWeight, double maxWeight) {
        return this.listOfVehicles.stream()
                .filter(
                        (v) ->
                                v.getWeight() >= minWeight &&
                                        v.getWeight() <= maxWeight
                )
                .toList();
    }


    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Vehicle> vehicles;

        file = ResourceUtils.getFile("classpath:vehicles_100.json");
        vehicles = objectMapper.readValue(file, new TypeReference<List<Vehicle>>() {
        });

        listOfVehicles = vehicles;
    }
}
