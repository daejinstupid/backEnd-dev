package com.example.demo.cafeFeature.service.Impl;

import com.example.demo.cafe.dto.CafeDto;
import com.example.demo.cafeFeature.dto.CafeFeatureDto;
import com.example.demo.cafeFeature.entity.CafeFeature;
import com.example.demo.cafeFeature.mapper.CafeFeatureMapper;
import com.example.demo.cafe.mapper.CafeImgMapper;
import com.example.demo.cafeFeature.service.CafeFeatureService;
import com.example.demo.feature.entity.Feature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CafeFeatureServiceImpl implements CafeFeatureService {

    private final CafeFeatureMapper cafeFeatureMapper;
    private final CafeImgMapper cafeImgMapper;


    @Override
    public CafeFeatureDto.CafeFeatureResponseDto insertCafeFeatures(CafeFeatureDto.CafeFeatureRequestDto cafeFeatureRequestDto, String userName) {
        int cafeId = cafeImgMapper.findCafeIdByUserName(userName);

        int[] featureIds = new int[]{22, 23, 24, 25, 26, 27}; // 특성 ID 배열
        boolean[] featureValues = new boolean[]{
                cafeFeatureRequestDto.getComfortableSeats(),
                cafeFeatureRequestDto.getHasDesserts(),
                cafeFeatureRequestDto.getQuiet(),
                cafeFeatureRequestDto.getNoMusic(),
                cafeFeatureRequestDto.getSentimental(),
                cafeFeatureRequestDto.getHasPowerOutlets()

        };

        // 각 특성에 대해 반복
        for (int i = 0; i < featureIds.length; i++) {
            if (featureValues[i]) {
                cafeFeatureMapper.insertCafeFeatures(cafeId, featureIds[i]);
            }

        }
        List<Integer> featureIdss = cafeFeatureMapper.findFeatureIdByCafeId(cafeId);
        return new CafeFeatureDto.CafeFeatureResponseDto(featureIdss);
    }

    @Override
    public CafeFeatureDto.CafeFeatureResponseDto selectCafeFeature(String userName) {
        int cafeId = cafeImgMapper.findCafeIdByUserName(userName);
        List<Integer> featureIds = cafeFeatureMapper.findFeatureIdByCafeId(cafeId);
        return new CafeFeatureDto.CafeFeatureResponseDto(featureIds);
    }

    @Override
    public void deleteFeatures(String userName) {
        int cafeId = cafeImgMapper.findCafeIdByUserName(userName);
        cafeFeatureMapper.deleteFeatures(cafeId);
    }


}
