package com.example.demo.cafeFeature.mapper;

import com.example.demo.cafe.dto.CafeDto;
import com.example.demo.cafeFeature.dto.CafeFeatureDto;
import com.example.demo.cafeFeature.entity.CafeFeature;
import com.example.demo.feature.entity.Feature;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CafeFeatureMapper {
    void insertCafeFeatures(int cafeId, int featureId);
    List<Integer> findFeatureIdByCafeId(int cafeId);
    void deleteFeatures(int cafeId);


}
