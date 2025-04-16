package com.xpronto.webgestao.api.dtos.request;

import com.xpronto.webgestao.api.dtos.annotation.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfirmUserRequest {
    @NotBlank
    @ValidPassword
    private String password;
}
