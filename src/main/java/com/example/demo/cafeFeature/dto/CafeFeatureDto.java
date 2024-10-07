package com.example.demo.cafeFeature.dto;

import com.example.demo.cafeFeature.entity.CafeFeature;
import com.example.demo.cafeFeature.service.CafeFeatureService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CafeFeatureDto {
    @Data
    public static class MapSelectFeatureRequestDto{
        private String featureName;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeFeatureRequestDto{
        private Boolean everyhour;
        private Boolean roomforones;
        private Boolean seminar;
        private Boolean bakery;
        private Boolean parkinglot;
        private Boolean hasPowerOutlets;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeFeatureResponseDto{
        private List<Integer> featureIds;
    }
}
