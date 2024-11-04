package com.example.demo.cafe.mapper;

import com.example.demo.cafe.entity.Cafe;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CafeUpdateMapper {
    int updateCafeTel(String cafeTel, int cafeId);
    int updateCafeAddress(String address, double longitude, double latitude, int cafeId);
    int updateCafeTime(String startTime, String endTime, int cafeId);

    int updateCafeStudy(String study, int cafeId);

    void updateCafeStudyImg(byte[] studyImg, String studyImgMine, int cafeId);

    void updateCafeRepImg(byte[] cafeRepImg, String cafeRepImgMine, int cafeId);

}
