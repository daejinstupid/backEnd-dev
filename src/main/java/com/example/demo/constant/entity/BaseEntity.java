package com.example.demo.constant.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;

@Getter
@Setter
@Data
public class BaseEntity<T> {

    private T data;
    private Date createDate;
    private Date modifyDate;

}
