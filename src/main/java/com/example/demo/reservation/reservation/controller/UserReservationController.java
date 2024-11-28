package com.example.demo.reservation.reservation.controller;

import com.example.demo.cafe.service.CafeService;
import com.example.demo.constant.dto.ApiResponse;
import com.example.demo.constant.enums.CustomResponseCode;
import com.example.demo.reservation.reservation.dto.ReservationDto;
import com.example.demo.reservation.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/reservation")
@Slf4j
public class UserReservationController {

    private final ReservationService reservationService;
    private final CafeService cafeService;

    // 예약 생성
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<ReservationDto.UserReservationResponseDto>> createReservation(
            @RequestBody ReservationDto.UserReservationRequestDto userReservationRequestDto,
            Authentication authentication) {
        log.info("예약 생성 요청 받음");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        ReservationDto.UserReservationResponseDto reservationIds = reservationService.createReservation(userReservationRequestDto, username);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(reservationIds, CustomResponseCode.SUCCESS));
    }

    // 예약 가능한 시간 조회
    @GetMapping("/time/{date}/{tableId}")
    public ResponseEntity<ApiResponse<List<ReservationDto.TimeSlotResponseDto>>> getRevTimeInfo(
            @PathVariable String date,
            @PathVariable int tableId) {
        log.info("시간 가져오기 시작");
        List<ReservationDto.TimeSlotResponseDto> timeSlotResponseDto = reservationService.getAvailableTimeSlots(date, tableId);
        log.info("시간 슬롯 조회 결과: {}", timeSlotResponseDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(timeSlotResponseDto, CustomResponseCode.SUCCESS));
    }

    // 예약할 카페 이름 & 테이블 조회
    @GetMapping("/cafe/{cafeId}")
    public ResponseEntity<ApiResponse<ReservationDto.RevCafeInfoResponseDto>> getRevCafeInfo(
            @PathVariable int cafeId) {
        log.info("예약할 카페 정보 조회 시작");
        ReservationDto.RevCafeInfoResponseDto revCafeInfoResponseDto = reservationService.getRevCafeInfo(cafeId);
        log.info("카페 정보 조회 결과: {}", revCafeInfoResponseDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(revCafeInfoResponseDto, CustomResponseCode.SUCCESS));
    }

    // 완료된 예약 조회
    @GetMapping("/list/finish")
    public ResponseEntity<ApiResponse<List<ReservationDto.UserReadFinishReservResponseDto>>> readUserFinishReservation(
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<ReservationDto.UserReadFinishReservResponseDto> finishs = reservationService.finishReservations(userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(finishs, CustomResponseCode.SUCCESS));
    }

    // 진행 중인 예약 조회
    @GetMapping("/list/state")
    public ResponseEntity<ApiResponse<List<ReservationDto.UserReadFinishReservResponseDto>>> readUserReservationIng(
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<ReservationDto.UserReadFinishReservResponseDto> proceeds = reservationService.proceedReservations(userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(proceeds, CustomResponseCode.SUCCESS));
    }

    // 특정 예약 현황 조회
    @GetMapping("/now/{reservationId}")
    public ResponseEntity<ApiResponse<ReservationDto.UserReservationStatusResponseDto>> readUserReservationStatus(
            @PathVariable int reservationId,
            Authentication authentication) {
        log.info("예약 현황 상태 조회 시작");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ReservationDto.UserReservationStatusResponseDto userReservationStatusResponseDto = reservationService.reservationStatus(userDetails.getUsername(), reservationId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(userReservationStatusResponseDto, CustomResponseCode.SUCCESS));
    }

    // 예약 취소 사유 조회
    @GetMapping("/now/cancel/{reservationId}")
    public ResponseEntity<ApiResponse<ReservationDto.CancelReasonResponDto>> readCancelReason(
            @PathVariable int reservationId,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("취소 사유 조회 시작");
        ReservationDto.CancelReasonResponDto cancelReasonResponseDto = reservationService.cancelReason(reservationId, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(cancelReasonResponseDto, CustomResponseCode.SUCCESS));
    }

    // 리뷰 저장
    @PostMapping("/review")
    public ResponseEntity<ApiResponse<String>> saveReview(
            @RequestBody ReservationDto.ReviewRequestDto reviewRequestDto,
            Authentication authentication) {
        log.info("리뷰 저장 요청 받음: {}", reviewRequestDto);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        // 리뷰 저장 서비스 호출
        reservationService.saveReview(reviewRequestDto, username);

        return ResponseEntity.ok().body(ApiResponse.createSuccess("리뷰 저장 성공", CustomResponseCode.SUCCESS));
    }

    // 특정 카페의 리뷰 조회
    @GetMapping("/review/{cafeId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCafeReviews(@PathVariable int cafeId) {
        log.info("카페 리뷰 리스트 요청: Cafe ID = {}", cafeId);

        // 서비스 호출을 통해 리뷰 데이터 가져오기
        List<ReservationDto.CafeReviewResponseDto> reviews = reservationService.getReviewsByCafeId(cafeId);

        // 서비스 호출을 통해 카페 이름 가져오기
        String cafeName = cafeService.findCafeNameByCafeId(cafeId);

        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("cafeName", cafeName); // 카페 이름 추가
        response.put("reviews", reviews);  // 리뷰 데이터 추가

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseCode.SUCCESS));
    }

}
