package com.xpronto.webgestao.domain.services.geolocation;

import com.xpronto.webgestao.domain.model.Address;

public interface GeocodingService {
    Geolocation getCoordinates(Address address);
}
