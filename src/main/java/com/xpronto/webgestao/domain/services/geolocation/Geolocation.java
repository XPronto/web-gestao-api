package com.xpronto.webgestao.domain.services.geolocation;

import java.math.BigDecimal;
import java.util.Objects;

public class Geolocation {
    private final BigDecimal latitude;
    private final BigDecimal longitude;

    public Geolocation(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Geolocation))
            return false;
        Geolocation that = (Geolocation) o;
        return Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "Geolocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
