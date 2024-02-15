package com.bootcampW22.EjercicioGlobal.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMultiVehiclesRequestDto {
    @Valid
    @NotNull
    private List<VehicleDto> vehicles;
}
