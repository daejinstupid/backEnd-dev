package com.example.demo.cafeTable.entity;

import com.example.demo.constant.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CafeTable extends BaseEntity {
    private int tableId;
    private int cafeId;
    private String tableType;
    private String tableNumber;
    private String status;
}