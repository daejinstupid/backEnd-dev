package com.example.demo.cafe.entity;

import com.example.demo.userss.entity.Users;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class CafeImg {

    private int cafeImgId;
    private int cafeId; // 외래키
    private byte[] cafeDetailImg;
    private Date createDate;
    private Date modifyDate;
    private String cafeDetailImgMine;

}
