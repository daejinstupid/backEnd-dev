package com.example.demo.reservation.cancelReason.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelReason {
    private String cancelReasonId;
    private String cancelContent;
    private Date createDate;
    private Date modifyDate;
}