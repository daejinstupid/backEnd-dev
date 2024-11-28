package com.example.demo.reservation.reservation.dto;

import com.example.demo.cafeTable.dto.CafeTableDto;
import com.example.demo.reservation.reservation.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

public class ReservationDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserReservationRequestDto {
        private int tableId;
        private List<Reservation.TimeSlot> reserveTime;
        private int personCnt;
        private String reserveDate;
    }

    @Data
    @NoArgsConstructor
    public static class UserReservationResponseDto {
        private List<Integer> reservationIds;

        public UserReservationResponseDto(List<Integer> reservationIds) {
            this.reservationIds = reservationIds;
        }
    }

    @Data
    public static class RejectReservationRequestDto {
        private List<Integer> reservationIds;
        private String rejectReasonId;
    }

    @Data
    public static class CancelReservationRequestDto {
        private List<Integer> reservationIds;
        private String cancelReasonId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RevCafeInfoResponseDto {
        private String cafeName;
        private Map<String, List<CafeTableDto.CafeTableInfoResponseDto>> tableInfo;
        private String studyImg;
        private String studyImgMine;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TimeSlotResponseDto {
        private String reserveStart;
        private String reserveEnd;
        private String available;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ManagerReservationResponseDto {
        private List<Integer> reservationIds;
        private String userRealName;
        private String tableNumber;
        private String tableType;
        private int personCnt;
        private String reserveStart;
        private String reserveEnd;
        private String reserveDate;
        private String status;
    }

    @Data
    public static class ConfAndFinReservationRequestDto {
        private List<Integer> reservationIds;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserReadFinishReservResponseDto {
        private List<Integer> reservationIds;
        private String cafeName;
        private String tableNumber;
        private String reserveStart;
        private String reserveEnd;
        private String reserveDate;
        private String state;
        private String cafeRepImg;
        private String reviewText;
        private Integer rating;

        public UserReadFinishReservResponseDto(Reservation reservation, List<Integer> reservationIds, String tableNumber, String cafeName, String cafeRepImg) {
            this.reservationIds = reservationIds;
            this.cafeName = cafeName;
            this.tableNumber = tableNumber;
            this.reserveStart = reservation.getReserveStart();
            this.reserveEnd = reservation.getReserveEnd();
            this.reserveDate = reservation.getReserveDate();
            this.state = reservation.getStatus();
            this.cafeRepImg = cafeRepImg;
            this.reviewText = reservation.getReviewText();
            this.rating = reservation.getRating();
        }
    }

    @Data
    @NoArgsConstructor
    @Builder
    public static class UserReservationStatusResponseDto {
        private String userRealName;
        private String status;
        private int reservationId;

        public UserReservationStatusResponseDto(String userRealName, String status, int reservationId) {
            this.userRealName = userRealName;
            this.status = status;
            this.reservationId = reservationId;
        }
    }

    @Data
    @NoArgsConstructor
    @Builder
    public static class CancelReasonResponDto {
        private int reservationId;
        private String cancelContent;
        private String cafeTel;
        private String userRealName;

        public CancelReasonResponDto(int reservationId, String cancelContent, String cafeTel, String userRealName) {
            this.reservationId = reservationId;
            this.cancelContent = cancelContent;
            this.cafeTel = cafeTel;
            this.userRealName = userRealName;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReviewRequestDto {
        private int reservationId;
        private String reviewText;
        private Integer rating;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReviewResponseDto {
        private int reservationId;
        private String reviewText;
        private Integer rating;
    }

    // 추가된 DTO: 특정 카페의 리뷰 목록을 반환하기 위한 DTO
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CafeReviewResponseDto {
        private String userRealName; // 리뷰를 작성한 사용자 이름
        private String reviewText;  // 리뷰 내용
        private Integer rating;     // 별점
        private String reviewDate;  // 리뷰 작성 날짜
    }
}
