package com.tfg.backend.model.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String token;
    private String nuevaPassword;
}
