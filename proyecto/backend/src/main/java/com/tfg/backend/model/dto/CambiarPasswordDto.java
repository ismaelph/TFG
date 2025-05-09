package com.tfg.backend.model.dto;

import lombok.Data;

@Data
public class CambiarPasswordDto {
    private String passwordActual;
    private String nuevaPassword;
}
