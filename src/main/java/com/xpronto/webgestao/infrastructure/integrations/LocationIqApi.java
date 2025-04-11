package com.xpronto.webgestao.infrastructure.integrations;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@FeignClient("location-iq-api")
public interface LocationIqApi {
    @GetMapping("/search?key={api-key}&format=json&limit=1&q={address}&countrycodes=br")
    public List<Location> getCoordinates(@PathVariable("api-key") String apiKey, @PathVariable String address);

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Location {
        private String placeId;
        private String licence;
        private BigDecimal lat;
        private BigDecimal lon;
        private String displayName;
        private List<String> boundingBox;
        private Double importance;
    }
}
