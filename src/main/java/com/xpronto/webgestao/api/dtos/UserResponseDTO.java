package com.xpronto.webgestao.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
}
