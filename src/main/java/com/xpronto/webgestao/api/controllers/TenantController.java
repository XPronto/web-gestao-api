package com.xpronto.webgestao.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xpronto.webgestao.api.dtos.AddressDTO;
import com.xpronto.webgestao.api.dtos.request.CreateTenantRequest;
import com.xpronto.webgestao.domain.model.Tenant;
import com.xpronto.webgestao.domain.usecases.createtenant.CreateTenantCommand;
import com.xpronto.webgestao.domain.usecases.createtenant.CreateTenantUseCase;
import com.xpronto.webgestao.domain.usecases.createtenant.CreateTenantCommand.AddressData;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantController extends BaseController {
    private final CreateTenantUseCase createTenantUseCase;

    @PostMapping
    public ResponseEntity<Void> createTenant(@RequestBody CreateTenantRequest body) throws Exception {
        AddressDTO addressDTO = body.getAddress();
        var addressData = new AddressData(addressDTO.getStreet(), addressDTO.getNumber(),
                addressDTO.getNeighborhood(), addressDTO.getCity(), addressDTO.getState(), addressDTO.getZipCode(),
                addressDTO.getLatitude(), addressDTO.getLongitude());

        var createTenantCommand = new CreateTenantCommand(body.getTenantName(), body.getAdminName(), body.getAdminEmail(), body.getAdminPassword(), addressData);

        Tenant tenant = createTenantUseCase.execute(createTenantCommand);

        return ResponseEntity.created(locationUri(tenant.getId())).build();
    }

}
