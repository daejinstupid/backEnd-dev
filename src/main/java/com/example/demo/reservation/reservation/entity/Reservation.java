package com.example.demo.reservation.reservation.entity;

import com.example.demo.constant.entity.BaseEntity;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.Date;


@Data
@AllArgsConstructor
@Builder
public class Reservation extends BaseEntity {


    private int reservationId;
    private int tableId;
    private int cafeId;
    private int userId;
    private String reserveStart;
    private String reserveEnd;
    private int personCnt;
    private String status;
    private String reserveDate;
    private String cancelReasonId;



    @Data
    public static class TimeSlot {
        private String reserveStart;
        private String reserveEnd;
    }

}
