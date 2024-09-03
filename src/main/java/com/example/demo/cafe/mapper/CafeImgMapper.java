package com.example.demo.cafe.mapper;

import com.example.demo.cafe.dto.CafeImgDto;
import com.example.demo.cafe.entity.CafeImg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CafeImgMapper {


    int findCafeIdByUserName(String userName);

    void insertCafeImg(CafeImgDto.CafeRegisterImgRequestDto imgDto);

    List<CafeImg> findImgsByCafeId(int cafeId);
}
