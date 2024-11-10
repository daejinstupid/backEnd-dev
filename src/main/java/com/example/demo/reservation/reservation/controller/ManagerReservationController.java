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
@RequestMapping("/manager/reservation")
@Slf4j
public class ManagerReservationController {

    private final ReservationService reservationService;

    @GetMapping("/read/date/{date}")
    public ResponseEntity<ApiResponse<List<ReservationDto.ManagerReservationResponseDto>>> getDateReservation(@PathVariable String date, Authentication authentication){
        log.info("날짜별 조회 시작");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userName = userDetails.getUsername();

        List<ReservationDto.ManagerReservationResponseDto> responseDto = reservationService.getDateReservation(date, userName);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(responseDto, CustomResponseCode.SUCCESS));
    }

    @GetMapping("/read/before")
    public ResponseEntity<ApiResponse<List<ReservationDto.ManagerReservationResponseDto>>> getBeforeReservation(Authentication authentication){
        log.info("예정된 예약 조회 시작");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userName = userDetails.getUsername();

        List<ReservationDto.ManagerReservationResponseDto> responseDto = reservationService.getBeforeReservation(userName);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(responseDto, CustomResponseCode.SUCCESS));
    }

    @GetMapping("/read/ing")
    public ResponseEntity<ApiResponse<List<ReservationDto.ManagerReservationResponseDto>>> getIngReservation(Authentication authentication){
        log.info("진행중인 예약 조회 시작");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userName = userDetails.getUsername();

        List<ReservationDto.ManagerReservationResponseDto> responseDto = reservationService.getIngReservation(userName);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(responseDto, CustomResponseCode.SUCCESS));
    }

    @PatchMapping("/confirm")
    public ResponseEntity<ApiResponse<Boolean>> confirmReservation(@RequestBody ReservationDto.ConfAndFinReservationRequestDto requestDto, Authentication authentication) {
        log.info("예약 확정 시작");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userName = userDetails.getUsername();

        Boolean check = reservationService.changeConfirmReservation(requestDto, userName);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(check, CustomResponseCode.SUCCESS));

    }

    // 예약 전 거절 (reject)
    @PatchMapping("/reject")
    public ResponseEntity<ApiResponse<Boolean>> rejectReservation(
            @RequestBody ReservationDto.RejectReservationRequestDto rejectReservationRequestDto,
            Authentication authentication) {

        log.info("예약 거절 시작");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userName = userDetails.getUsername();

        Boolean isRejected = reservationService.rejectReservation(rejectReservationRequestDto, userName);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(isRejected, CustomResponseCode.SUCCESS));
    }


    @PatchMapping("/cancel")
    public ResponseEntity<ApiResponse<Boolean>> cancelReservation(@RequestBody ReservationDto.CancelReservationRequestDto cancelReservationRequestDto, Authentication authentication) {
        log.info("예약 취소 시작");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userName = userDetails.getUsername();

        Boolean check = reservationService.changeCancelReservation(cancelReservationRequestDto, userName);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(check, CustomResponseCode.SUCCESS));

    }

    @PatchMapping("/finish")
    public ResponseEntity<ApiResponse<Boolean>> finishReservation(@RequestBody ReservationDto.ConfAndFinReservationRequestDto requestDto, Authentication authentication) {
        log.info("이용 종료 시작");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userName = userDetails.getUsername();

        Boolean check = reservationService.changeFinishReservation(requestDto, userName);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(check, CustomResponseCode.SUCCESS));

    }
}
