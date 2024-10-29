package com.example.demo.reservation.cancelReason.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelReasonDto {
    private int cafeId;
    private String cancelContent;
    private Long cancelCount;
}