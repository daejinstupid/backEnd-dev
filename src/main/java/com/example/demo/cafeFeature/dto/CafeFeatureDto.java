package com.example.demo.cafeFeature.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CafeFeatureDto {

    @Data
    public static class MapSelectFeatureRequestDto {
        private String featureName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder // 빌더 패턴을 사용하여 기본값 설정
    public static class CafeFeatureRequestDto {

        @Builder.Default
        private Boolean comfortableSeats = false;

        @Builder.Default
        private Boolean hasDesserts = false;

        @Builder.Default
        private Boolean quiet = false;

        @Builder.Default
        private Boolean noMusic = false;

        @Builder.Default
        private Boolean sentimental = false;

        @Builder.Default
        private Boolean hasPowerOutlets = false;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeFeatureResponseDto {
        private List<Integer> featureIds;
    }
}
