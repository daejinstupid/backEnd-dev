package com.example.demo.reservation.reservation.service;

import com.example.demo.reservation.reservation.dto.ReservationDto;
import org.springframework.security.core.Authentication;


import java.util.List;

public interface ReservationService {

    // user
    ReservationDto.UserReservationResponseDto createReservation(ReservationDto.UserReservationRequestDto userReservationRequestDto, String userName);
    List<ReservationDto.TimeSlotResponseDto> getAvailableTimeSlots(String reserveDate, int tableId);
    ReservationDto.RevCafeInfoResponseDto getRevCafeInfo(int cafeId);
    ReservationDto.UserReservationStatusResponseDto reservationStatus(String userName, int reservationId);
    ReservationDto.CancelReasonResponDto cancelReason(int reservationId, String userName);

    // manager
    List<ReservationDto.ManagerReservationResponseDto> getDateReservation(String date, String userName);
    List<ReservationDto.ManagerReservationResponseDto> getBeforeReservation(String userName);
    List<ReservationDto.ManagerReservationResponseDto> getIngReservation(String userName);

    Boolean rejectReservation(ReservationDto.RejectReservationRequestDto requestDto, String userName);
    Boolean changeConfirmReservation(ReservationDto.ConfAndFinReservationRequestDto requestDto, String userName);
    Boolean changeCancelReservation(ReservationDto.CancelReservationRequestDto requestDto, String userName);
    Boolean changeFinishReservation(ReservationDto.ConfAndFinReservationRequestDto requestDto, String userName);

    List<ReservationDto.UserReadFinishReservResponseDto> finishReservations(String userName);
    List<ReservationDto.UserReadFinishReservResponseDto> proceedReservations(String userName);

    void saveReview(ReservationDto.ReviewRequestDto reviewRequestDto, String userName);

    List<ReservationDto.CafeReviewResponseDto> getReviewsByCafeId(int cafeId);
}
