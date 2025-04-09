package com.xpronto.webgestao.api.dtos.request;

import com.xpronto.webgestao.api.dtos.AddressDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateTenantRequest {
    private String tenantName;
    private AddressDTO address;
    private String adminName;
    private String adminEmail;
    private String adminPassword;
}
