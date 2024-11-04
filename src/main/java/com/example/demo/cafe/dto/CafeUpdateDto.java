package com.example.demo.cafe.dto;

import com.example.demo.cafe.entity.Cafe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

@Slf4j
public class CafeUpdateDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeTelRequestDto{
        private String cafeTel;

        public CafeTelRequestDto(Cafe cafe){
            this.cafeTel = cafe.getCafeTel();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeTelResponseDto{
        private int cafeId;
        private String cafeTel;

        public CafeTelResponseDto(Cafe cafe){
            this.cafeId = cafe.getCafeId();
            this.cafeTel = cafe.getCafeTel();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeLocationRequestDto{
        private String address;
        private Double longitude;
        private Double latitude;

        public CafeLocationRequestDto(Cafe cafe){
            this.address = cafe.getAddress();
            this.longitude = cafe.getLongitude();
            this.latitude = cafe.getLatitude();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeLocationResponseDto{
        private int cafeId;
        private String address;
        private Double longitude;
        private Double latitude;

        public CafeLocationResponseDto(Cafe cafe){
            this.cafeId = cafe.getCafeId();
            this.address = cafe.getAddress();
            this.longitude = cafe.getLongitude();
            this.latitude = cafe.getLatitude();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeTimeRequestDto{
        private String startTime;
        private String endTime;

        public CafeTimeRequestDto(Cafe cafe){
            this.startTime = cafe.getStartTime();
            this.endTime = cafe.getEndTime();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeTimeResponseDto{
        private String startTime;
        private String endTime;
        private int cafeId;

        public CafeTimeResponseDto(Cafe cafe){
            this.startTime = cafe.getStartTime();
            this.endTime = cafe.getEndTime();
            this.cafeId = cafe.getCafeId();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeStudyRequestDto{
        private String study;

        public CafeStudyRequestDto(Cafe cafe){
            this.study = cafe.getStudy();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeStudyResponseDto{
        private String study;
        public CafeStudyResponseDto(Cafe cafe){
            this.study = cafe.getStudy();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeStudyImgRequestDto {
        private byte[] studyImg;
        private String studyImgMine;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeStudyImgResponseDto {
        private String studyImg;
        private String studyImgMine;

        public CafeStudyImgResponseDto(Cafe cafe) {
            this.studyImg = Base64.getEncoder().encodeToString(cafe.getStudyImg());
            this.studyImgMine = cafe.getStudyImgMine();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeRepImgResponseDto {
        private String cafeRepImg;
        private String cafeRepImgMine;
        public CafeRepImgResponseDto(Cafe cafe){
            this.cafeRepImg = Base64.getEncoder().encodeToString(cafe.getCafeRepImg());
            this.cafeRepImgMine =  cafe.getCafeRepImgMine();
        }
    }

}
