package com.xpronto.webgestao.domain.usecases.createtenant;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;

import com.xpronto.webgestao.domain.model.Address;
import com.xpronto.webgestao.domain.model.Permission;
import com.xpronto.webgestao.domain.model.PermissionSet;
import com.xpronto.webgestao.domain.model.Tenant;
import com.xpronto.webgestao.domain.model.User;
import com.xpronto.webgestao.domain.model.valueobject.Document;
import com.xpronto.webgestao.domain.repositories.PermissionRepository;
import com.xpronto.webgestao.domain.repositories.PermissionSetRepository;
import com.xpronto.webgestao.domain.repositories.TenantRepository;
import com.xpronto.webgestao.domain.repositories.UserRepository;
import com.xpronto.webgestao.domain.services.geolocation.GeocodingService;
import com.xpronto.webgestao.domain.services.geolocation.Geolocation;
import com.xpronto.webgestao.domain.usecases.UseCase;
import com.xpronto.webgestao.domain.usecases.createtenant.CreateTenantCommand.AddressData;
import com.xpronto.webgestao.domain.usecases.createtenant.CreateTenantCommand.UserData;

public class CreateTenantUseCase implements UseCase<CreateTenantCommand, Tenant> {
    private final UserRepository userRepository;
    private final PermissionSetRepository permissionSetRepository;
    private final TenantRepository tenantRepository;
    private final PermissionRepository permissionRepository;
    private final GeocodingService geocodingService;

    public CreateTenantUseCase(
            UserRepository userRepository,
            PermissionSetRepository permissionSetRepository,
            TenantRepository tenantRepository,
            PermissionRepository permissionRepository,
            GeocodingService geocodingService) {
        this.userRepository = userRepository;
        this.permissionSetRepository = permissionSetRepository;
        this.tenantRepository = tenantRepository;
        this.permissionRepository = permissionRepository;
        this.geocodingService = geocodingService;
    }

    @Override
    public Tenant execute(CreateTenantCommand command) throws Exception {
        Document document = Document.create(command.getDocument(), Document.verifyType(command.getDocument()));
        UserData adminData = command.getUser();

        if (userRepository.existsByEmail(adminData.getEmail())) {
            throw new Exception("Email already registered");
        }

        if (tenantRepository.existsByDocument(document.getNumber())) {
            throw new Exception("Document already registered");
        }

        AddressData addressData = command.getAddress();

        Address address = new Address(addressData.getStreet(), addressData.getNumber(), addressData.getNeighborhood(),
                addressData.getCity(), addressData.getState(), addressData.getZipCode(), null, null);

        Geolocation geolocation = geocodingService.getCoordinates(address);

        if (geolocation != null) {
            address.setLatitude(geolocation.getLatitude());
            address.setLongitude(geolocation.getLongitude());
        }

        Tenant tenant = new Tenant();
        tenant.setName(command.getTenantName());
        tenant.setDocument(document);
        tenant.setContactEmail(command.getContactEmail());
        tenant.setContactPhone(command.getContactPhone());
        tenant.setOpeningTime(command.getOpeningTime());
        tenant.setClosingTime(command.getClosingTime());
        tenant.setLegalName(command.getLegalName());
        tenant.setWebsite(command.getWebsite());
        tenant.setLogoUrl(command.getLogoUrl());
        tenant.setAddress(address);
        tenant.setCreatedAt(OffsetDateTime.now());

        Tenant createdTenant = tenantRepository.save(tenant);

        List<Permission> permissions = permissionRepository.findAll();

        PermissionSet adminPermissionSet = new PermissionSet();
        adminPermissionSet.setName("Administrador do Sistema");
        adminPermissionSet.setTenant(createdTenant);
        adminPermissionSet.setPermissions(new HashSet<>(permissions));
        adminPermissionSet.setCreatedAt(OffsetDateTime.now());

        PermissionSet createdPermissionSet = permissionSetRepository.save(adminPermissionSet);

        User user = new User();
        user.setFirtstName(adminData.getFirstName());
        user.setLastName(adminData.getLastName());
        user.setEmail(adminData.getEmail());
        user.setPasswordHash(adminData.getPassword());
        user.setTenant(createdTenant);
        user.setPermissionSets(List.of(createdPermissionSet));
        user.setCreatedAt(OffsetDateTime.now());

        userRepository.save(user);

        return createdTenant;
    }

}
