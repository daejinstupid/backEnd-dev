package com.example.demo.cafe.service;

import com.example.demo.cafe.dto.CafeUpdateDto;
import org.springframework.web.multipart.MultipartFile;

public interface CafeUpdateService {
    CafeUpdateDto.CafeTelResponseDto cafeTelUpdate(CafeUpdateDto.CafeTelRequestDto cafeTel, String userName);

    CafeUpdateDto.CafeLocationResponseDto cafeAddressUpdate(CafeUpdateDto.CafeLocationRequestDto cafeAddress, String userName);
    CafeUpdateDto.CafeTimeResponseDto cafeTimeUpdate(CafeUpdateDto.CafeTimeRequestDto cafeTime, String userName);

    CafeUpdateDto.CafeStudyResponseDto cafeStudyUpdate(CafeUpdateDto.CafeStudyRequestDto cafeStudy, String userName);

    CafeUpdateDto.CafeStudyImgResponseDto cafeStudyImgUpdate(MultipartFile cafeStudyImg, String userName);
    CafeUpdateDto.CafeRepImgResponseDto cafeRepImgUpdate(MultipartFile cafeRepImg, String userName);

}
