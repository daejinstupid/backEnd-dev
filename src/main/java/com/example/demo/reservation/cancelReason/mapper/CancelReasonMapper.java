package com.example.demo.reservation.cancelReason.mapper;

import com.example.demo.reservation.cancelReason.dto.CancelReasonDto;
import com.example.demo.reservation.cancelReason.entity.CancelReason;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CancelReasonMapper {
    CancelReason getOneCancelReason(String cancelReasonId);
    CancelReason getReservationCancelReason(int reservationId);
    List<CancelReasonDto> getCancelReasonStatsByCafeId(@Param("cafeId") int cafeId);
}
