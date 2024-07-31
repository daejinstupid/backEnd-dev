package com.example.demo.reservation.reservation.controller;

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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/reservation")
@Slf4j
public class UserReservationController {

    private final ReservationService reservationService;

    // 예약 생성
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<ReservationDto.UserReservationResponseDto>> createReservation(@RequestBody ReservationDto.UserReservationRequestDto userReservationRequestDto, Authentication authentication) {
        log.info("예약 생성 요청 받음");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        ReservationDto.UserReservationResponseDto reservationIds = reservationService.createReservation(userReservationRequestDto,username);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(reservationIds, CustomResponseCode.SUCCESS));
    }
//    @CrossOrigin(origins = "http://localhost:3000")

    @GetMapping("/time/{date}/{tableId}")
    public ResponseEntity<ApiResponse<List<ReservationDto.TimeSlotResponseDto>>> getRevTimeInfo(@PathVariable String date, @PathVariable int tableId, Authentication authentication){
        log.info("시간 가져오기 시작");
        List<ReservationDto.TimeSlotResponseDto> timeSlotResponseDto = reservationService.getAvailableTimeSlots(date, tableId);
        log.info(timeSlotResponseDto.toString());
        log.info("apiresponse: "+ApiResponse.createSuccess(timeSlotResponseDto, CustomResponseCode.SUCCESS));
        return ResponseEntity.ok().body(ApiResponse.createSuccess(timeSlotResponseDto, CustomResponseCode.SUCCESS));
    }

    // 예약할 카페 이름&테이블 조회
//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/cafe/{cafeId}")
    public ResponseEntity<ApiResponse<ReservationDto.RevCafeInfoResponseDto>> getRevCafeInfo(@PathVariable int cafeId, Authentication authentication){
        log.info("예약할 카페 정보 조회 시작");
        ReservationDto.RevCafeInfoResponseDto revCafeInfoResponseDto = reservationService.getRevCafeInfo(cafeId);
        log.info(revCafeInfoResponseDto.toString());
        log.info("apiresponse: "+ApiResponse.createSuccess(revCafeInfoResponseDto, CustomResponseCode.SUCCESS));
        return ResponseEntity.ok().body(ApiResponse.createSuccess(revCafeInfoResponseDto, CustomResponseCode.SUCCESS));
    }

    @GetMapping("/list/finish")
    public ResponseEntity<ApiResponse<List<ReservationDto.UserReadFinishReservResponseDto>>> readUserFinishReservation(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<ReservationDto.UserReadFinishReservResponseDto> finishs = reservationService.finishReservations(userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(finishs,CustomResponseCode.SUCCESS));
    }

    @GetMapping("/list/state")
    public ResponseEntity<ApiResponse<List<ReservationDto.UserReadFinishReservResponseDto>>> readUserReservationIng(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<ReservationDto.UserReadFinishReservResponseDto> proceeds = reservationService.proceedReservations(userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(proceeds,CustomResponseCode.SUCCESS));
    }
    @GetMapping("/now/{reservationId}")
    public ResponseEntity<ApiResponse<ReservationDto.UserReservationStatusResponseDto>> readUserReservationStatus(
            @PathVariable int reservationId,
            Authentication authentication){
        log.info("예약 현황 상태 조회 시작");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ReservationDto.UserReservationStatusResponseDto userReservationStatusResponseDto = reservationService.reservationStatus(userDetails.getUsername(),reservationId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(userReservationStatusResponseDto, CustomResponseCode.SUCCESS));
    }
    @GetMapping("/now/cancle/{reservationId}")
    public ResponseEntity<ApiResponse<ReservationDto.CancleReasonResponDto>> readCancleReason(@PathVariable int reservationId, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("취소 페이지 시작");
      ReservationDto.CancleReasonResponDto cancleReasonResponseDto = reservationService.cancleReason(reservationId,userDetails.getUsername());
        return  ResponseEntity.ok().body(ApiResponse.createSuccess(cancleReasonResponseDto, CustomResponseCode.SUCCESS));
    }
}
