package com.example.demo.cafeTable.dto;

import com.example.demo.cafeTable.entity.CafeTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class CafeTableDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CafeTableInfoResponseDto {
        private int tableId;
        private String tableNumber;
        private String tableType;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CafeTableInsertRequestDto {
        private int cafeId;
        private String tableType;
        private String tableNumber;
        private String status;
        private Date createDate;
        private Date modifyDate;

        public CafeTableInsertRequestDto(CafeTable cafeTable){
            this.cafeId = cafeTable.getCafeId();
            this.tableType = cafeTable.getTableType();
            this.tableNumber = cafeTable.getTableNumber();
            this.status = cafeTable.getStatus();
            this.createDate = cafeTable.getCreateDate();
            this.modifyDate = cafeTable.getModifyDate();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CafeTableResponseDto {
        private int tabledId;
        private int cafeId;
        private String tableType;
        private String tableNumber;
        private String status;

        public CafeTableResponseDto(CafeTable cafeTable){
            this.tabledId = cafeTable.getTableId();
            this.cafeId = cafeTable.getCafeId();
            this.tableType = cafeTable.getTableType();
            this.tableNumber = cafeTable.getTableNumber();
            this.status = cafeTable.getStatus();
        }
    }

}
