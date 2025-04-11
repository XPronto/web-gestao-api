package com.xpronto.webgestao.domain.model;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.xpronto.webgestao.domain.model.valueobject.Document;

public class Tenant {
    private UUID id;
    private String name;
    private String contactEmail;
    private String contactPhone;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String legalName;
    private String website;
    private String logoUrl;
    private Address address;
    private Document document;
    private List<User> users;
    private List<PermissionSet> permissionSets;
    private OffsetDateTime createdAt;

    public Tenant(UUID id, String name, Document document, String contactEmail, String contactPhone,
            LocalTime openingTime, LocalTime closingTime, String legalName, String website, String logoUrl,
            Address address, List<User> users, List<PermissionSet> permissionSets, OffsetDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.legalName = legalName;
        this.website = website;
        this.logoUrl = logoUrl;
        this.address = address;
        this.users = users;
        this.permissionSets = permissionSets;
        this.createdAt = createdAt;
    }

    public Tenant() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<PermissionSet> getPermissionSets() {
        return permissionSets;
    }

    public void setPermissionSets(List<PermissionSet> permissionSets) {
        this.permissionSets = permissionSets;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

}
