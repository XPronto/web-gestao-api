package com.xpronto.webgestao.infrastructure.persistence.mapper;

import java.math.BigDecimal;

import com.xpronto.webgestao.domain.model.Address;
import com.xpronto.webgestao.domain.model.Tenant;
import com.xpronto.webgestao.domain.model.valueobject.Document;
import com.xpronto.webgestao.infrastructure.persistence.entity.AddressEmbeddable;
import com.xpronto.webgestao.infrastructure.persistence.entity.TenantEntity;

public class TenantMapper {

    public static Tenant toDomain(TenantEntity entity) {
        Address address = null;

        if (entity.getAddress() != null) {
            AddressEmbeddable emb = entity.getAddress();

            BigDecimal latitude = emb.getLatitude();
            BigDecimal longitude = emb.getLongitude();

            address = new Address(
                    emb.getStreet(),
                    emb.getNumber(),
                    emb.getNeighborhood(),
                    emb.getCity(),
                    emb.getState(),
                    emb.getZipCode(),
                    latitude,
                    longitude);
        }

        Document document = Document.create(entity.getDocument(), entity.getDocumentType());

        return new Tenant(entity.getId(), entity.getName(), document, entity.getEmail(),
                entity.getPhone(), entity.getOpeningTime(), entity.getClosingTime(), entity.getLegalName(),
                entity.getWebsite(), entity.getLogoUrl(), address, null, null, entity.getCreatedAt());
    }

    public static TenantEntity toEntity(Tenant tenant) {
        Document document = tenant.getDocument();

        TenantEntity entity = new TenantEntity();
        entity.setId(tenant.getId());
        entity.setName(tenant.getName());
        entity.setCreatedAt(tenant.getCreatedAt());
        entity.setDocument(document.getNumber());
        entity.setDocumentType(document.getType());
        entity.setEmail(tenant.getContactEmail());
        entity.setPhone(tenant.getContactPhone());
        entity.setOpeningTime(tenant.getOpeningTime());
        entity.setClosingTime(tenant.getClosingTime());
        entity.setLegalName(tenant.getLegalName());
        entity.setWebsite(tenant.getWebsite());
        entity.setLogoUrl(tenant.getLogoUrl());

        Address addr = tenant.getAddress();

        if (addr != null) {
            AddressEmbeddable emb = new AddressEmbeddable();
            emb.setStreet(addr.getStreet());
            emb.setNumber(addr.getNumber());
            emb.setNeighborhood(addr.getNeighborhood());
            emb.setCity(addr.getCity());
            emb.setState(addr.getState());
            emb.setZipCode(addr.getZipCode());
            emb.setLatitude(addr.getLatitude());
            emb.setLongitude(addr.getLongitude());

            entity.setAddress(emb);
        }

        return entity;
    }
}
