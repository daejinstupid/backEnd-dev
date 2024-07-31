package com.example.demo.cafe.service.impl;

import com.example.demo.cafe.dto.CafeUpdateDto;
import com.example.demo.cafe.entity.Cafe;
import com.example.demo.cafe.mapper.CafeImgMapper;
import com.example.demo.cafe.mapper.CafeMapper;
import com.example.demo.cafe.mapper.CafeUpdateMapper;
import com.example.demo.cafe.service.CafeUpdateService;
import com.example.demo.constant.dto.ApiResponse;
import com.example.demo.constant.enums.CustomResponseCode;
import com.example.demo.constant.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimeType;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CafeUpdateServiceImpl implements CafeUpdateService {

    private final CafeUpdateMapper cafeUpdateMapper;
    private final CafeImgMapper cafeImgMapper;
    private final CafeMapper cafeMapper;

    private String extractMimeType(byte[] imageData) throws GeneralException{
        try (ByteArrayInputStream bais = new ByteArrayInputStream(imageData)) {
            ImageInputStream iis = ImageIO.createImageInputStream(bais);
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                throw new GeneralException(CustomResponseCode.CAFEIMG_READER_NOT_FOUND);
            }
            ImageReader reader = iter.next();
            return "image/" + reader.getFormatName().toLowerCase();
        } catch (IOException e) {
            throw new GeneralException(CustomResponseCode.IMG_EXTRACT_ERROR);
        }
    }

    private void validateCafeTime(CafeUpdateDto.CafeTimeRequestDto cafeTime) throws GeneralException {
        try {
            LocalTime startTime = LocalTime.parse(cafeTime.getStartTime());
            LocalTime endTime = LocalTime.parse(cafeTime.getEndTime());

            if (!startTime.isBefore(endTime)) {
                throw new GeneralException(CustomResponseCode.CAFE_OPERATOR_TIME_OVER);
            } else if (startTime.equals(endTime)) {
                throw new GeneralException(CustomResponseCode.CAFE_OPERATOR_TIME_EQUALS);
            }
        } catch (DateTimeParseException e) {
            throw new GeneralException(CustomResponseCode.CAFE_OPERATOR_TIME_MISMATCH);
        }
    }


    @Override
    public CafeUpdateDto.CafeTelResponseDto cafeTelUpdate(CafeUpdateDto.CafeTelRequestDto cafeTel, String userName) {
        int cafeId = cafeImgMapper.findCafeIdByUserName(userName);
        cafeUpdateMapper.updateCafeTel(cafeTel.getCafeTel(), cafeId);

        Cafe cafe = cafeMapper.getOneCafe(cafeId);
        return new CafeUpdateDto.CafeTelResponseDto(cafe);
    }

    @Override
    public CafeUpdateDto.CafeLocationResponseDto cafeAddressUpdate(CafeUpdateDto.CafeLocationRequestDto cafeAddress, String userName) {
        int cafeId = cafeImgMapper.findCafeIdByUserName(userName);
        cafeUpdateMapper.updateCafeAddress(cafeAddress.getAddress(), cafeAddress.getLongtitude(), cafeAddress.getLatitude(), cafeId);

        Cafe cafe = cafeMapper.getOneCafe(cafeId);
        return new CafeUpdateDto.CafeLocationResponseDto(cafe);
    }

    @Override
    public CafeUpdateDto.CafeTimeResponseDto cafeTimeUpdate(CafeUpdateDto.CafeTimeRequestDto cafeTime, String userName) {
        validateCafeTime(cafeTime);
        int cafeId = cafeImgMapper.findCafeIdByUserName(userName);

        cafeUpdateMapper.updateCafeTime(cafeTime.getStartTime(), cafeTime.getEndTime(), cafeId);
        Cafe cafe = cafeMapper.getOneCafe(cafeId);
        return new CafeUpdateDto.CafeTimeResponseDto(cafe);
    }

    @Override
    public CafeUpdateDto.CafeStudyResponseDto cafeStudyUpdate(CafeUpdateDto.CafeStudyRequestDto cafeStudy, String userName) {
        int cafeId = cafeImgMapper.findCafeIdByUserName(userName);
        cafeUpdateMapper.updateCafeStudy(cafeStudy.getStudy(), cafeId);
        Cafe cafe = cafeMapper.getOneCafe(cafeId);
        return new CafeUpdateDto.CafeStudyResponseDto(cafe);
    }

    @Override
    public CafeUpdateDto.CafeStudyImgResponseDto cafeStudyImgUpdate(MultipartFile cafeStudyImg, String userName) {
        int cafeId = cafeImgMapper.findCafeIdByUserName(userName);
        if (cafeStudyImg != null && !cafeStudyImg.isEmpty()) {
            try {
                byte[] studyImgBytes = cafeStudyImg.getBytes();
                String mimeType = extractMimeType(studyImgBytes); // MIME 타입 추출
                if (mimeType != null) {
                    cafeUpdateMapper.updateCafeStudyImg(studyImgBytes, mimeType, cafeId);
                }
            } catch (IOException e) {
                throw new GeneralException(CustomResponseCode.NO_CAFEIMG_DATA_READ);
            }
        }
        Cafe cafe = cafeMapper.getOneCafe(cafeId);
        return new CafeUpdateDto.CafeStudyImgResponseDto(cafe);
    }

    @Override
    public CafeUpdateDto.CafeRepImgResponseDto cafeRepImgUpdate(MultipartFile cafeRepImg, String userName) {
        int cafeId = cafeImgMapper.findCafeIdByUserName(userName);
        if (cafeRepImg != null && !cafeRepImg.isEmpty()) {
            try {
                byte[] cafeRepImgBytes = cafeRepImg.getBytes();
                String mimeType = extractMimeType(cafeRepImgBytes); // MIME 타입 추출
                if (mimeType != null) {
                    cafeUpdateMapper.updateCafeRepImg(cafeRepImgBytes, mimeType, cafeId);
                }
            } catch (IOException e) {
                throw new GeneralException(CustomResponseCode.NO_CAFEIMG_DATA_READ);
            }
        }
        Cafe cafe = cafeMapper.getOneCafe(cafeId);
        return new CafeUpdateDto.CafeRepImgResponseDto(cafe);
    }
}
