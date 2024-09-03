package com.example.demo.cafeFeature.service;

import com.example.demo.cafe.dto.CafeDto;
import com.example.demo.cafeFeature.dto.CafeFeatureDto;

import java.util.List;

public interface CafeFeatureService {
    CafeFeatureDto.CafeFeatureResponseDto insertCafeFeatures(CafeFeatureDto.CafeFeatureRequestDto cafeFeatureRequestDto, String userName);
    CafeFeatureDto.CafeFeatureResponseDto selectCafeFeature (String userName);
    void deleteFeatures(String userName);
}
