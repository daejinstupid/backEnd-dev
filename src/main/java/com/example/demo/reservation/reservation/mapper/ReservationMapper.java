package com.example.demo.reservation.reservation.mapper;

import com.example.demo.reservation.reservation.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationMapper {
    // user
    void createReservation(Reservation reservation);
    List<Reservation> getOneTableRev(String reserveDate, int tableId);

    // manager
    List<Reservation> getOneCafeOneDayRev(String date, int cafeId);
    List<Reservation> getOneCafeBeforeRev(int cafeId);
    List<Reservation> getOneCafeIngRev(int cafeId);

    Reservation getRevByRevId(int reservationId);
    void cofirmReservation(int reservationId);
    void cancleReservation(int reservationId, String cancleReasonId);
    void finishReservation(int reservationId);


    List<Reservation> getFinReservations(int userId);

    List<Reservation> getProceedReservations(int userId);

    Reservation getReservationRecent(int userId);

    int getReservationId(int userId, String reserveDate, String reserveStart);
}
