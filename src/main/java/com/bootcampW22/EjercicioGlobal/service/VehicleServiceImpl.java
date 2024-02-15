package com.bootcampW22.EjercicioGlobal.service;

import com.bootcampW22.EjercicioGlobal.dto.*;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.bootcampW22.EjercicioGlobal.exception.ConflictException;
import com.bootcampW22.EjercicioGlobal.exception.NotFoundException;
import com.bootcampW22.EjercicioGlobal.repository.IVehicleRepository;
import com.bootcampW22.EjercicioGlobal.repository.VehicleRepositoryImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements IVehicleService {

    IVehicleRepository vehicleRepository;
    ModelMapper modelMapper;

    public VehicleServiceImpl(VehicleRepositoryImpl vehicleRepository, ModelMapper modelMapper) {
        this.vehicleRepository = vehicleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<VehicleDto> searchAllVehicles() {
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        if (vehicleList.isEmpty()) {
            throw new NotFoundException("No se encontró ningun auto en el sistema.");
        }
        return vehicleList.stream()
                .map(this::convertVehicleToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleDto save(VehicleDto vehicleDto) {
        System.out.println(vehicleDto);
        Optional<Vehicle> vehicleWithId = this.vehicleRepository.findById(vehicleDto.getId());
        if (vehicleWithId.isPresent())
            throw new ConflictException("Identificador del vehículo ya existente");
        Vehicle newVehicle = this.vehicleRepository.save(modelMapper.map(vehicleDto, Vehicle.class));
        return this.modelMapper.map(newVehicle, VehicleDto.class);
    }

    @Override
    public List<VehicleDto> searchVehiclesByColorAndYear(String color, Integer year) {
        List<Vehicle> vehicles = this.vehicleRepository.findByColorAndYear(color, year);
        if (vehicles.isEmpty())
            throw new NotFoundException("No se encontraron vehículos con esos criterios");
        return vehicles.stream().map((v) -> modelMapper.map(v, VehicleDto.class)).toList();
    }

    @Override
    public List<VehicleDto> searchVehiclesByBrandAndBetweenStartDateAndEndDate(String brand, Integer startDate, Integer endDate) {
        List<Vehicle> vehicles = this.vehicleRepository.findByBrandAndBetweenStartDateAndEndDate(brand, startDate, endDate);
        if (vehicles.isEmpty())
            throw new NotFoundException("No se encontraron vehículos con esos criterios");
        return vehicles.stream().map((v) -> modelMapper.map(v, VehicleDto.class)).toList();
    }

    @Override
    public VehicleAverageSpeedResponseDto searchAverageSpeedByBrand(String brand) {
        List<Vehicle> vehiclesByBrand = this.vehicleRepository.findByBrand(brand);
        if (vehiclesByBrand.isEmpty())
            throw new NotFoundException("No se encontraron vehículos de esa marca");
        return new VehicleAverageSpeedResponseDto(this.vehicleRepository.findAverageSpeedByBrand(brand));
    }

    @Override
    public List<VehicleDto> multiSave(CreateMultiVehiclesRequestDto createMultiVehiclesRequestDto) {
        List<Long> ids = createMultiVehiclesRequestDto.getVehicles().stream().map(VehicleDto::getId).toList();
        List<Vehicle> vehiclesWithIds = this.vehicleRepository.findByIds(ids);
        if (!vehiclesWithIds.isEmpty())
            throw new ConflictException("Algún vehículo tiene un identificador ya existente");
        List<Vehicle> newVehicles = createMultiVehiclesRequestDto.getVehicles().stream().map((v) -> modelMapper.map(v, Vehicle.class)).toList();
        return this.vehicleRepository.multiSave(newVehicles).stream().map((v) -> modelMapper.map(v, VehicleDto.class)).toList();
    }

    @Override
    public VehicleDto updateMaxSpeed(Long id, UpdateMaxSpeedVehicleDto updateMaxSpeedVehicleDto) {
        Optional<Vehicle> vehicle = this.vehicleRepository.findById(id);
        if (vehicle.isEmpty())
            throw new NotFoundException("No se encontró el vehículo");
        Vehicle vehicleToUpdate = this.modelMapper.map(updateMaxSpeedVehicleDto, Vehicle.class);
        vehicleToUpdate.setId(id);
        return this.modelMapper.map(this.vehicleRepository.update(vehicleToUpdate), VehicleDto.class);
    }

    @Override
    public List<VehicleDto> searchByFuelType(String fuelType) {
        List<Vehicle> vehicles = this.vehicleRepository.findByFuelType(fuelType);
        if (vehicles.isEmpty())
            throw new NotFoundException("No se encontraron vehículos con ese tipo de combustible");
        return vehicles.stream().map((v) -> modelMapper.map(v, VehicleDto.class)).toList();
    }

    @Override
    public void delete(Long id) {
        Optional<Vehicle> vehicle = this.vehicleRepository.findById(id);
        if (vehicle.isEmpty())
            throw new NotFoundException("No se encontró el vehículo");
        this.vehicleRepository.delete(id);
    }

    @Override
    public List<VehicleDto> searchByTransmissionType(String transmissionType) {
        List<Vehicle> vehicles = this.vehicleRepository.findByTransmissionType(transmissionType);
        if (vehicles.isEmpty())
            throw new NotFoundException("No se encontraron vehículos con ese tipo de transmisión");
        return vehicles.stream().map((v) -> modelMapper.map(v, VehicleDto.class)).toList();
    }

    @Override
    public VehicleDto updateFuelType(Long id, UpdateFuelTypeVehicleDto updateFuelTypeVehicleDto) {
        Optional<Vehicle> vehicle = this.vehicleRepository.findById(id);
        if (vehicle.isEmpty())
            throw new NotFoundException("No se encontró el vehículo");
        Vehicle vehicleToUpdate = modelMapper.map(updateFuelTypeVehicleDto, Vehicle.class);
        vehicleToUpdate.setId(id);
        return modelMapper.map(this.vehicleRepository.update(vehicleToUpdate), VehicleDto.class);
    }

    @Override
    public VehicleAverageCapacityResponseDto searchAverageCapacityByBrand(String brand) {
        List<Vehicle> vehicles = this.vehicleRepository.findByBrand(brand);
        if (vehicles.isEmpty())
            throw new NotFoundException("No se encontraron vehículos de esa marca");
        double averageCapacity = this.vehicleRepository.findAverageCapacityByBrand(brand);
        return new VehicleAverageCapacityResponseDto(averageCapacity);
    }

    @Override
    public List<VehicleDto> searchByLengthAndWidth(String length, String width) {
        String[] lengthDimensions = length.split("-");
        String[] widthDimensions = width.split("-");
        List<Vehicle> vehicles = this.vehicleRepository.findByDimensions(
                Double.parseDouble(lengthDimensions[0]),
                Double.parseDouble(lengthDimensions[1]),
                Double.parseDouble(widthDimensions[0]),
                Double.parseDouble(widthDimensions[1])
        );
        if (vehicles.isEmpty())
            throw new NotFoundException("No se encontraron vehículos con esas dimensiones");
        return vehicles.stream().map((v) -> modelMapper.map(v, VehicleDto.class)).toList();
    }

    @Override
    public List<VehicleDto> searchByWeight(Double minWeight, Double maxWeight) {
        List<Vehicle> vehicles = this.vehicleRepository.findByWeight(
                minWeight,
                maxWeight
        );
        if (vehicles.isEmpty())
            throw new NotFoundException("No se encontraron vehículos en ese rango de peso");
        return vehicles.stream().map((v) -> modelMapper.map(v, VehicleDto.class)).toList();
    }

    private VehicleDto convertVehicleToDto(Vehicle v) {
        return new VehicleDto(
                v.getId(),
                v.getBrand(),
                v.getModel(),
                v.getRegistration(),
                v.getColor(),
                v.getYear(),
                v.getMax_speed(),
                v.getPassengers(),
                v.getFuel_type(),
                v.getTransmission(),
                v.getLength(),
                v.getWidth(),
                v.getWeight());
    }
}
