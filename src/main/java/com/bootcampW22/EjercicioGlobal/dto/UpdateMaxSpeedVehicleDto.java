package com.bootcampW22.EjercicioGlobal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMaxSpeedVehicleDto {
    @NotNull
    private Double max_speed;
}
