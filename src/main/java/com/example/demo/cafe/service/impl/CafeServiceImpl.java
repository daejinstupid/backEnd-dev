package com.example.demo.cafe.service.impl;


import com.example.demo.cafe.dto.CafeDto;
import com.example.demo.cafe.dto.CafeImgDto;
import com.example.demo.cafe.entity.Cafe;
import com.example.demo.cafe.entity.CafeImg;
import com.example.demo.cafeFeature.mapper.CafeFeatureMapper;
import com.example.demo.cafe.mapper.CafeImgMapper;
import com.example.demo.cafe.mapper.CafeMapper;
import com.example.demo.cafe.service.CafeService;
import com.example.demo.cafeFeature.entity.CafeFeature;
import com.example.demo.constant.enums.CustomResponseCode;
import com.example.demo.constant.exception.GeneralException;
import com.example.demo.feature.dto.FeatureDto;
import com.example.demo.feature.entity.Feature;
import com.example.demo.feature.mapper.FeatureMapper;
import com.example.demo.userss.entity.Users;
import com.example.demo.userss.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
public class CafeServiceImpl implements CafeService {


    private final CafeMapper cafeMapper;
    private final UsersMapper usersMapper;
    private final CafeImgMapper cafeImgMapper;
    private final FeatureMapper featureMapper;

    private String extractMimeType(byte[] imageData) {
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


    @Override
    public int findUserIdByCafeId(int userId) {
        return cafeMapper.findUserIdByCafeId(userId);
    }
    public String findCafeNameByCafeId(int cafeId){
        return cafeMapper.findCafeNameByCafeId(cafeId);
    }

    @Override
    @Transactional
    public void registerCafe(CafeDto.CafeRegisterRequestDto requestDto, String userName, MultipartFile cafeRepImgFile, MultipartFile studyImgFile) {

        Users user = usersMapper.getOneUsers(userName);
        requestDto.setUserId(user.getUserId());

        try {
            // 대표 이미지 처리
            if (cafeRepImgFile != null && !cafeRepImgFile.isEmpty()) {
                byte[] repImgBytes = cafeRepImgFile.getBytes();
                requestDto.setCafeRepImg(repImgBytes);
                String mimeType = extractMimeType(repImgBytes);
                if (mimeType != null) {
                    requestDto.setCafeRepImgMine(mimeType);
                }
            }

            // 스터디 이미지 처리
            if (studyImgFile != null && !studyImgFile.isEmpty()) {
                byte[] studyImgBytes = studyImgFile.getBytes();
                requestDto.setStudyImg(studyImgBytes);
                String mimeType = extractMimeType(studyImgBytes);
                if (mimeType != null) {
                    requestDto.setStudyImgMine(mimeType);
                }
            }
            cafeMapper.insertCafe(requestDto);
        } catch (IOException e) {
            throw new GeneralException(CustomResponseCode.NO_CAFEIMG_DATA_READ);
        }
    }

    @Override
    public CafeDto.CafeSearchDetailResponseDto searchCafeDetail(int cafeId) {
        Cafe cafe = cafeMapper.getOneCafe(cafeId);

        List<Feature> features = featureMapper.readFeatures(cafeId);
        List<FeatureDto.FeatureResponseDto> featureDto = features.stream().map(FeatureDto.FeatureResponseDto::new)
                .collect(Collectors.toList());
        log.info(featureDto.toString());
        List<CafeImg> imgs = cafeImgMapper.findImgsByCafeId(cafeId);
        return new CafeDto.CafeSearchDetailResponseDto(cafe,featureDto,imgs);
    }

    @Override
    public CafeDto.CafeReadBasicResponseDto findCafeBasicByUserId(String userName) {
        try {
            int cafeId = cafeImgMapper.findCafeIdByUserName(userName);
            Cafe cafe = cafeMapper.getOneCafe(cafeId);
            CafeDto.CafeReadBasicResponseDto responseDto = new CafeDto.CafeReadBasicResponseDto(cafe);
            responseDto.setCafeCheck(true);
            return responseDto;
        } catch (Exception e) {
            CafeDto.CafeReadBasicResponseDto responseDto = new CafeDto.CafeReadBasicResponseDto();
            responseDto.setCafeCheck(false);
            return responseDto;
        }
    }

    @Override
    public CafeDto.CafeReadDetailResponseDto findCafeDetailByUserId(String userName) {
        int cafeId = cafeImgMapper.findCafeIdByUserName(userName);
        Cafe cafe =  cafeMapper.getOneCafe(cafeId);
        List<CafeImg> imgs = cafeImgMapper.findImgsByCafeId(cafeId);
        List<byte[]> imgList = imgs.stream().map(CafeImg::getCafeDetailImg).collect(Collectors.toList());
        return new CafeDto.CafeReadDetailResponseDto(cafe,imgList);
    }

    @Override
    public CafeDto.CafeReadSettingResponseDto findCafeSettingByUserId(String userName) {
        int cafeId = cafeImgMapper.findCafeIdByUserName(userName);
        Cafe cafe =  cafeMapper.getOneCafe(cafeId);

        return new CafeDto.CafeReadSettingResponseDto(cafe);
    }

    @Override
    public void deleteCafe(String userName) {
        int cafeId = cafeImgMapper.findCafeIdByUserName(userName);
        cafeMapper.cafeDelete(cafeId);
    }

}
