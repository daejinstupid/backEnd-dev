package com.example.demo.cafe.dto;

import com.example.demo.cafe.entity.Cafe;

import com.example.demo.cafe.entity.CafeImg;
import com.example.demo.cafeFeature.dto.CafeFeatureDto;
import com.example.demo.cafeTable.dto.CafeTableDto;
import com.example.demo.feature.dto.FeatureDto;
import com.example.demo.reservation.reservation.dto.ReservationDto;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CafeDto {

    @Getter
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeRegisterRequestDto{
        private int cafeId;
        private String cafeName;
        private String cafeType;
        private String startTime;
        private String endTime;
        private String address;
        private Double longtitude;
        private Double latitude;
        private String cafeTel;
        private String study;
        private int userId;
        private byte[] cafeRepImg;
        private byte[] studyImg;
        private Date createDate;
        private Date modifyDate;
        private String cafeRepImgMine;
        private String studyImgMine;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeRegisterResponseDto{
        private int cafeId;
        private String cafeName;
        private int userId;
        private byte[] studyImg;
        public CafeRegisterResponseDto(int userId, byte[] studyImg){
            this.userId = userId;
            this.studyImg = studyImg;
        }
    }

    @Data
    public static class CafeSearchResponseDto{
        private  int cafeId;
        private String cafeName;
        private String cafeReqImg;
        private String startTime;
        private String endTime;
        private String address;
        private String cafeTel;
        private Double longtitude;
        private Double latitude;
        private Double distance;

        public CafeSearchResponseDto(Cafe cafe,String cafeReqImg){
            this.cafeId = cafe.getCafeId();
            this.cafeName = cafe.getCafeName();
            this.cafeReqImg = cafeReqImg;
            this.startTime = cafe.getStartTime();
            this.endTime = cafe.getEndTime();
            this.address = cafe.getAddress();
            this.cafeTel = cafe.getCafeTel();
            this.longtitude = cafe.getLongtitude();
            this.latitude = cafe.getLatitude();
        }
    }


/* 매니저 - 카페 조회 */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeReadBasicResponseDto{
        private int cafeId;
        private String cafeName;
        private String cafeType;
        private String address;
        private String detailAddress;
        private String cafeTel;
        private Double longtitude;
        private Double latitude;
        private String cafeRepImg;
        private String cafeRepImgMine;
        private int userId;
        private Boolean CafeCheck;
        public CafeReadBasicResponseDto(Cafe cafe) {
            this.cafeId = cafe.getCafeId();
            this.cafeName = cafe.getCafeName();
            this.cafeType = cafe.getCafeType();
            this.address = cafe.getAddress();
            this.cafeTel = cafe.getCafeTel();
            this.userId = cafe.getUserId();
            this.longtitude = cafe.getLongtitude();
            this.latitude = cafe.getLatitude();
            this.cafeRepImg = Base64.getEncoder().encodeToString(cafe.getCafeRepImg());
            this.cafeRepImgMine =  cafe.getCafeRepImgMine();
            if (cafe.getAddress() != null) {
                String[] addressParts = cafe.getAddress().split(",", 2);
                this.address = addressParts[0]; // 첫 번째 부분은 주소
                if (addressParts.length > 1) {
                    this.detailAddress = addressParts[1]; // 두 번째 부분은 상세 주소
                }
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeReadDetailResponseDto{
        private int cafeId;
        private String startTime;
        private String endTime;
        private int userId;
        private List<String> detailImgs;

        public CafeReadDetailResponseDto(Cafe cafe, List<byte[]> imgs){
            this.cafeId = cafe.getCafeId();
            this.startTime = cafe.getStartTime();
            this.endTime = cafe.getEndTime();
            this.userId = cafe.getUserId();
            this.detailImgs = imgs.stream().map(
                    img -> Base64.getEncoder().encodeToString(img)
            ).collect(Collectors.toList());
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeDetailsResponseWrapper {
        private CafeDto.CafeReadDetailResponseDto detailResponse;
        private CafeFeatureDto.CafeFeatureResponseDto featureResponse;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeReadSettingResponseDto{
        private int cafeId;
        private String study;
        private int userId;
        private String studyImg;
        private String studyImgMine;

        public CafeReadSettingResponseDto(Cafe cafe){
            this.cafeId = cafe.getCafeId();
            this.study = cafe.getStudy();
            this.userId = cafe.getUserId();
            this.studyImg = Base64.getEncoder().encodeToString(cafe.getStudyImg());
            this.studyImgMine = cafe.getStudyImgMine();
        }

    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeSettingResponseWrapper {
        private CafeDto.CafeReadSettingResponseDto SettingResponse;
        private ReservationDto.RevCafeInfoResponseDto CafeTableResponse;
    }

    @Builder
    @Data
    public static class CafeTimeResponseDto{
        private String startTime;
        private String endTime;

        public CafeTimeResponseDto(String startTime, String endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CafeSearchDetailResponseDto{
        private int cafeId;
        private String cafeName;
        private String address;
        private String startTime;
        private String endTime;
        private String cafeTel;
        private List<FeatureDto.FeatureResponseDto> features;
        private String study;
        private List<String> detailImgs;

        public CafeSearchDetailResponseDto(Cafe cafe, List<FeatureDto.FeatureResponseDto> features, List<CafeImg> imgs){
            this.cafeName = cafe.getCafeName();
            this.address = cafe.getAddress();
            this.startTime = cafe.getStartTime();
            this.endTime = cafe.getEndTime();
            this.cafeTel = cafe.getCafeTel();
            this.features = features;
            this.study = cafe.getStudy();
            this.detailImgs = imgs.stream()
                    .map(img -> Base64.getEncoder().encodeToString(img.getCafeDetailImg()))
                    .collect(Collectors.toList());
        }
    }

}
