package com.example.demo.reservation.reservation.entity;

import com.example.demo.constant.entity.BaseEntity;
import lombok.*;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
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

    // 리뷰 관련 필드 추가
    private String reviewText; // 리뷰 내용
    private Integer rating; // 별점 (1~5)

    // 사용자 실명 추가
    private String userRealName;

    @Data
    public static class TimeSlot {
        private String reserveStart;
        private String reserveEnd;
    }
}
