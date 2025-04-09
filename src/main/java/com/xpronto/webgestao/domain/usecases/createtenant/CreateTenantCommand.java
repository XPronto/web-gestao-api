package com.xpronto.webgestao.domain.usecases.createtenant;

import java.math.BigDecimal;

public class CreateTenantCommand {
    private final String tenantName;
    private final String adminName;
    private final String adminEmail;
    private final String adminPassword;
    private final AddressData address;

    public CreateTenantCommand(String tenantName, String adminName, String adminEmail, String adminPassword, AddressData address) {
        this.tenantName = tenantName;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.address = address;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public AddressData getAddress() {
        return address;
    }

    public static class AddressData {
        private final String street;
        private final String number;
        private final String neighborhood;
        private final String city;
        private final String state;
        private final String zipCode;
        private final BigDecimal latitude;
        private final BigDecimal longitude;

        public AddressData(String street, String number, String neighborhood, String city, String state, String zipCode, BigDecimal latitude, BigDecimal longitude) {
            this.street = street;
            this.number = number;
            this.neighborhood = neighborhood;
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public String getStreet() {
            return street;
        }

        public String getNumber() {
            return number;
        }

        public String getNeighborhood() {
            return neighborhood;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public String getZipCode() {
            return zipCode;
        }

        public BigDecimal getLatitude() {
            return latitude;
        }

        public BigDecimal getLongitude() {
            return longitude;
        }
    }
}
