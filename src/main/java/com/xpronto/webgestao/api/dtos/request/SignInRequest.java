package com.xpronto.webgestao.api.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignInRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
