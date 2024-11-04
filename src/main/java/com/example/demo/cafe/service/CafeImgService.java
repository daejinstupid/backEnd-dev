package com.example.demo.cafe.service;

import com.example.demo.cafe.dto.CafeDto;
import com.example.demo.cafe.dto.CafeImgDto;

import java.util.List;

public interface CafeImgService {
    void insertCafeImg(List<CafeImgDto.CafeRegisterImgRequestDto> cafeImgDtos, String userName);

    int findCafeIdByUserName(String userName);
}
