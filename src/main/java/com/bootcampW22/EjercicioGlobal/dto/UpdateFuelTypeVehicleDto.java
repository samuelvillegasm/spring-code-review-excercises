package com.bootcampW22.EjercicioGlobal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFuelTypeVehicleDto {
    @NotNull
    private String fuel_type;
}
