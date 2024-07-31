package com.example.demo.cafeFeature.entity;

import lombok.Getter;

import java.util.Date;

@Getter
public class CafeFeature {

    private int featureId; // 외래키 PK
    private int cafeId; // 외래키
    private Date createDate;
    private Date modifyDate;

}
