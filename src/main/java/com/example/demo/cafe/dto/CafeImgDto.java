package com.example.demo.cafe.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

public class CafeImgDto {

    @Getter
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CafeRegisterImgRequestDto{
        private int cafeId;
        private byte[] cafeDetailImg;
        private Date createDate;
        private Date modifyDate;
        private String cafeDetailImgMine;
    }

}
