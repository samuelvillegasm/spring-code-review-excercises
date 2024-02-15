package com.bootcampW22.EjercicioGlobal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleDto {
    Long id;
    @NotNull
    String brand;
    @NotNull
    String model;
    @NotNull
    String registration;
    @NotNull
    String color;
    @NotNull
    Integer year;
    @NotNull
    Double max_speed;
    @NotNull
    Integer passengers;
    @NotNull
    String fuel_type;
    @NotNull
    String transmission;
    @NotNull
    Double length;
    @NotNull
    Double width;
    @NotNull
    Double weight;
}