package com.xpronto.webgestao.domain.model;

import java.util.UUID;

public class Permission {
    private final UUID id;
    private final String code;
    private final String description;

    public Permission(UUID id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
