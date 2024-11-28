package com.example.demo.constant.enums;

import lombok.Getter;

@Getter
public enum CustomResponseCode {

    //성공(1000)
    SUCCESS(true, 1000, "요청에 성공했습니다."),

    //bad request(2000)
    BAD_JWT(false, 2000, "JWT 토큰이 잘못되었습니다."),
    NO_TOKEN(false, 2001, "JWT 토큰이 포함되어있지 않습니다."),
    AUTHENTICATION_FAILED(false, 2002, "정상적인 JWT가 아닙니다"),
    NO_RESERVATION_CAFE(false, 2003, "카페 예약이 불가능한 카페입니다."),
    NO_RESERVATION_TIME(false, 2004, "카페 예약이 불가능한 시간입니다."),
    NO_CHECK_DATE(false, 2005, "조회가 불가능한 날짜입니다."),
    NO_RESERVATION(false, 2006, "해당 예약은 존재하지 않습니다."),
    NO_CANCELREASON(false, 2007, "잘못된 카페 취소 사유입니다."),
    NO_CAFE_MANAGER(false, 2008, "해당 점주의 카페가 아닙니다."),
    IMG_EXTRACT_ERROR(false, 2009, "이미지 MIME 타입 추출 중 오류 발생."),
    NO_CAFEIMG_DATA_READ(false, 2010, "이미지 데이터를 읽을 수 없습니다."),
    ALREADY_CAFE_EXIST(false, 2011, "해당 점주는 이미 카페를 등록하였습니다."),

    //unauthorized(권한 없을 때)(3000)
    EXPIRED_JWT(false, 3000, "만료된 토큰입니다."),
    NO_AUTHENTICATION(false, 3001, "권한이 없는 유저입니다."),
    NOT_EQUALS_PASSWORD(false, 3002, "비밀번호가 틀렸습니다."),

    //not found(4000)
    USER_NOT_FOUND(false, 4000, "해당 유저를 찾을 수 없습니다."),
    CAFE_NOT_FOUND(false, 4001, "해당 카페를 찾을 수 없습니다."),
    RESERVATION_NOT_FOUND(false, 4002, "해당 예약을 찾을 수 없습니다."),
    CAFETABLE_NOT_FOUND(false, 4003, "해당 카페테이블을 찾을 수 없습니다."),
    CAFEIMG_READER_NOT_FOUND(false, 4004, "주어진 데이터에 대한 이미지 리더를 찾을 수 없습니다."),

    //internal server error(기타 에러)(5000)
    IMG_OVER_SELECT(false, 5000, "최대 5장의 이미지만 업로드 가능합니다"),
    CREATE_RESERVATION_FAILED(false, 5001, "카페 예약 생성을 실패하였습니다."),
    COFIRM_RESERVATION_FAILED(false, 5002, "카페 예약 확정을 실패하였습니다."),
    CANCEL_RESERVATION_FAILED(false, 5003, "카페 예약 취소를 실패하였습니다."),
    CAFE_OPERATOR_TIME_OVER(false, 5004, "카페 종료 시간은 시작 시간 이후여야 합니다"),
    CAFE_OPERATOR_TIME_MISMATCH(false, 5005, "카페 운영시간 형식이 맞지 않습니다"),
    CAFE_OPERATOR_TIME_EQUALS(false, 5006, "카페 시작시간과 종료시간이 같습니다."),

    // 예약 거절 실패 추가
    REJECT_RESERVATION_FAILED(false, 5007, "예약 거절 실패"),

    // 예약이 이미 확정된 경우 추가
    RESERVATION_ALREADY_CONFIRMED(false, 6001, "이미 확정된 예약입니다."),
    REVIEW_SAVE_FAILED(false, 6002, "리뷰 저장 실패");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    CustomResponseCode(boolean isSuccess, int code, String message) {
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
    }
}
