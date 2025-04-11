package com.xpronto.webgestao.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xpronto.webgestao.api.dtos.AddressDTO;
import com.xpronto.webgestao.api.dtos.UserDTO;
import com.xpronto.webgestao.api.dtos.request.CreateTenantRequest;
import com.xpronto.webgestao.domain.model.Tenant;
import com.xpronto.webgestao.domain.usecases.createtenant.CreateTenantCommand;
import com.xpronto.webgestao.domain.usecases.createtenant.CreateTenantUseCase;
import com.xpronto.webgestao.domain.usecases.createtenant.CreateTenantCommand.AddressData;
import com.xpronto.webgestao.domain.usecases.createtenant.CreateTenantCommand.UserData;

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
        UserDTO userDTO = body.getUser();

        var addressData = new AddressData(addressDTO.getStreet(), addressDTO.getNumber(), addressDTO.getNeighborhood(),
                addressDTO.getCity(), addressDTO.getState(), addressDTO.getZipCode());
        var userData = new UserData(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                userDTO.getPassword());

        var createTenantCommand = new CreateTenantCommand(body.getTenantName(), body.getDocument(),
                body.getContactEmail(), body.getContactPhone(), body.getOpeningTime(), body.getClosingTime(),
                body.getLegalName(), body.getWebsite(), body.getLogoUrl(), addressData, userData);

        Tenant tenant = createTenantUseCase.execute(createTenantCommand);

        return ResponseEntity.created(locationUri(tenant.getId())).build();
    }

}
