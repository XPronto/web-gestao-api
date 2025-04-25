package com.xpronto.webgestao.domain.usecases.createuser;

import java.util.UUID;

public class CreateUserCommand {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final UUID tenantId;

    public CreateUserCommand(String firstName, String lastName, String email, UUID tenantId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.tenantId = tenantId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public UUID getTenantId() {
        return tenantId;
    }
}
