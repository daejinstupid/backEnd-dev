package com.example.demo.search.dto;

import com.example.demo.cafe.dto.CafeDto;
import com.example.demo.cafe.dto.PageDto;
import com.example.demo.cafe.entity.Cafe;
import com.example.demo.cafeFeature.dto.CafeFeatureDto;
import lombok.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class SearchDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchRequestDto{
        private String cafeType;
        private String studyEnable;
        private String people;
        private String proceed;
        private List<String> features;
        private String startTime;
        private String endTime;
        private String preferSeat;
        private String word;
        private Double longitude;
        private Double latitude;
    }

    @Builder
    @Data
    public static class SearchResponseDto{
        private List<CafeDto.CafeSearchResponseDto> searchCafes;
        private PageDto pager;

        public SearchResponseDto(List<CafeDto.CafeSearchResponseDto> searchCafes, PageDto pager ){
            this.searchCafes = searchCafes;
            this.pager = pager;
        }

    }
}
