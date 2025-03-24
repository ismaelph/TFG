package com.tfg.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorDto {
    private String msg;

    public static ErrorDto from(String msg) {
        return new ErrorDto(msg);
    }

}
