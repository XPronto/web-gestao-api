package com.xpronto.webgestao.domain.usecases.createtenant;

import java.time.LocalTime;

public class CreateTenantCommand {
    private final String tenantName;
    private final String document;
    private final String contactEmail;
    private final String contactPhone;
    private final LocalTime openingTime;
    private final LocalTime closingTime;
    private final String legalName;
    private final String website;
    private final String logoUrl;
    private final AddressData address;
    private final UserData user;

    public CreateTenantCommand(String tenantName, String document, String contactEmail, String contactPhone,
            LocalTime openingTime, LocalTime closingTime, String legalName, String website, String logoUrl,
            AddressData address, UserData user) {
        this.tenantName = tenantName;
        this.document = document;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.legalName = legalName;
        this.website = website;
        this.logoUrl = logoUrl;
        this.address = address;
        this.user = user;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getDocument() {
        return document;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public String getLegalName() {
        return legalName;
    }

    public String getWebsite() {
        return website;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public AddressData getAddress() {
        return address;
    }

    public UserData getUser() {
        return user;
    }

    public static class UserData {
        private final String firstName;
        private final String lastName;
        private final String email;
        private final String password;

        public UserData(String firstName, String lastName, String email, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
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

        public String getPassword() {
            return password;
        }
    }

    public static class AddressData {
        private final String street;
        private final String number;
        private final String neighborhood;
        private final String city;
        private final String state;
        private final String zipCode;

        public AddressData(String street, String number, String neighborhood, String city, String state, String zipCode) {
            this.street = street;
            this.number = number;
            this.neighborhood = neighborhood;
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
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
    }
}
