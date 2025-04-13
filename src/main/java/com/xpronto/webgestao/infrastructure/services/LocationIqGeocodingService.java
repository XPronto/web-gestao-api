package com.xpronto.webgestao.infrastructure.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xpronto.webgestao.domain.model.Address;
import com.xpronto.webgestao.domain.services.geolocation.GeocodingService;
import com.xpronto.webgestao.domain.services.geolocation.Geolocation;
import com.xpronto.webgestao.infrastructure.integrations.LocationIqApi;
import com.xpronto.webgestao.infrastructure.integrations.LocationIqApi.Location;

@Service
public class LocationIqGeocodingService implements GeocodingService {
    @Value("${api.api-keys.location-iq-api-key}")
    private String apiKey;

    @Autowired
    private LocationIqApi locationIqApi;

    @Override
    public Geolocation getCoordinates(Address address) {
        String formattedStreet = address.getStreet() + ", " + address.getNumber() + " - " + address.getNeighborhood() + ", " + address.getCity() + " - " + address.getState() + ", " + address.getZipCode();

        List<Location> locations = locationIqApi.getCoordinates(apiKey, formattedStreet);

        if (locations.isEmpty()) return null;

        Location loc = locations.get(0);

        return new Geolocation(loc.getLat(), loc.getLon());
    }
}
