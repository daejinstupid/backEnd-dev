package com.example.demo.reservation.cancleReason.mapper;

import com.example.demo.reservation.cancleReason.entity.CancleReason;
import com.example.demo.reservation.reservation.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CancleReasonMapper {
    CancleReason getOneCancleReason(String cancleReasonId);
    CancleReason getReservationCancleReason(int reservationId);
}
