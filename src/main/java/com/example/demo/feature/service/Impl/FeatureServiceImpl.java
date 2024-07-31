package com.example.demo.feature.service.Impl;

import com.example.demo.feature.entity.Feature;
import com.example.demo.feature.mapper.FeatureMapper;
import com.example.demo.feature.service.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeatureServiceImpl implements FeatureService {

    private final FeatureMapper featureMapper;
    @Override
    public String getFeatureName(int featureId) {

        Feature feature = featureMapper.getFeature(featureId);
        return feature.getFeatureName();
    }
}
