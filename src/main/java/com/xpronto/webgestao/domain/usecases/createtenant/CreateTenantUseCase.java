package com.xpronto.webgestao.domain.usecases.createtenant;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;

import com.xpronto.webgestao.domain.model.Address;
import com.xpronto.webgestao.domain.model.Permission;
import com.xpronto.webgestao.domain.model.PermissionSet;
import com.xpronto.webgestao.domain.model.Tenant;
import com.xpronto.webgestao.domain.model.User;
import com.xpronto.webgestao.domain.repositories.PermissionRepository;
import com.xpronto.webgestao.domain.repositories.PermissionSetRepository;
import com.xpronto.webgestao.domain.repositories.TenantRepository;
import com.xpronto.webgestao.domain.repositories.UserRepository;
import com.xpronto.webgestao.domain.usecases.UseCase;
import com.xpronto.webgestao.domain.usecases.createtenant.CreateTenantCommand.AddressData;

public class CreateTenantUseCase implements UseCase<CreateTenantCommand, Tenant> {
    private final UserRepository userRepository;
    private final PermissionSetRepository permissionSetRepository;
    private final TenantRepository tenantRepository;
    private final PermissionRepository permissionRepository;

    public CreateTenantUseCase(
            UserRepository userRepository,
            PermissionSetRepository permissionSetRepository,
            TenantRepository tenantRepository,
            PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionSetRepository = permissionSetRepository;
        this.tenantRepository = tenantRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Tenant execute(CreateTenantCommand command) throws Exception {
        AddressData addressData = command.getAddress();

        Address address = new Address(addressData.getStreet(), addressData.getNumber(), addressData.getNeighborhood(),
                addressData.getCity(), addressData.getState(), addressData.getZipCode(), addressData.getLatitude(),
                addressData.getLongitude());

        Tenant tenant = new Tenant();
        tenant.setName(command.getTenantName());
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
        user.setName(command.getAdminName());
        user.setEmail(command.getAdminEmail());
        user.setPasswordHash(command.getAdminPassword());
        user.setTenant(createdTenant);
        user.setPermissionSets(List.of(createdPermissionSet));
        user.setCreatedAt(OffsetDateTime.now());

        userRepository.save(user);

        return createdTenant;
    }

}
