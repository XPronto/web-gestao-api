package com.xpronto.webgestao.api.dtos.request;

import java.time.LocalTime;

import com.xpronto.webgestao.api.dtos.AddressDTO;
import com.xpronto.webgestao.api.dtos.UserDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateTenantRequest {
    @NotBlank
    private String tenantName;

    @NotBlank
    private String document;

    @NotBlank
    @Email
    private String contactEmail;

    @NotBlank
    private String contactPhone;

    @NotNull
    private LocalTime openingTime;

    @NotNull
    private LocalTime closingTime;

    private String legalName;
    private String website;
    private String logoUrl;

    @NotNull
    @Valid
    private UserDTO user;

    @NotNull
    @Valid
    private AddressDTO address;
}
