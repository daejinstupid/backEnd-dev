package com.example.demo.reservation.reservation.service.impl;

import com.example.demo.cafe.dto.CafeDto;
import com.example.demo.cafe.entity.Cafe;
import com.example.demo.cafe.mapper.CafeMapper;
import com.example.demo.cafe.service.CafeImgService;
import com.example.demo.cafeTable.dto.CafeTableDto;
import com.example.demo.cafeTable.entity.CafeTable;
import com.example.demo.cafeTable.mapper.CafeTableMapper;
import com.example.demo.cafeTable.service.CafeTableService;
import com.example.demo.constant.enums.CustomResponseCode;
import com.example.demo.constant.exception.GeneralException;
import com.example.demo.reservation.cancleReason.entity.CancleReason;
import com.example.demo.reservation.cancleReason.mapper.CancleReasonMapper;
import com.example.demo.reservation.reservation.dto.ReservationDto;
import com.example.demo.reservation.reservation.entity.Reservation;
import com.example.demo.reservation.reservation.mapper.ReservationMapper;
import com.example.demo.reservation.reservation.service.ReservationService;
import com.example.demo.userss.entity.Users;
import com.example.demo.userss.mapper.UsersMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationMapper reservationMapper;
    private final CafeMapper cafeMapper;
    private final CafeTableMapper cafeTableMapper;
    private final UsersMapper usersMapper;
    private final CafeTableService cafeTableService;
    private final CafeImgService cafeImgService;
    private final CancleReasonMapper cancleReasonMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReservationDto.UserReservationResponseDto createReservation(ReservationDto.UserReservationRequestDto requestDto, String userName) {
        log.info("서비스 시작");

        // 존재하는 카페테이블인지 확인
        CafeTable cafeTable = cafeTableMapper.getOneCafeTable(requestDto.getTableId());
        if(cafeTable == null){
            throw new GeneralException(CustomResponseCode.CAFETABLE_NOT_FOUND);
        }

        // 존재하는 유저인지 확인
        Users user = usersMapper.getOneUsers(userName);
        if(user == null){
            throw new GeneralException(CustomResponseCode.USER_NOT_FOUND);
        }

        log.info(requestDto.getReserveDate()); // 지금 date

        // 예약 중복인지 확인
        List<Reservation> reservationTime = reservationMapper.getOneTableRev(requestDto.getReserveDate(), requestDto.getTableId());
        log.info(reservationTime.toString());
        List<Integer> ids = new ArrayList<>();
        for (Reservation.TimeSlot timeSlot : requestDto.getReserveTime()) {
            String requestStart = timeSlot.getReserveStart();
            boolean isChecked = false;

            for (Reservation reservation : reservationTime) {
                String reservationStart = reservation.getReserveStart();

                if (reservationStart.equals(requestStart)) {
                    isChecked = true;
                    break;
                }
            }

            if (isChecked) {
                throw new GeneralException(CustomResponseCode.NO_RESERVATION_TIME);
            }

            Reservation saveReservation = Reservation.builder()
                    .tableId(requestDto.getTableId())
                    .userId(user.getUserId())
                    .cafeId(cafeTable.getCafeId())
                    .reserveStart(requestStart)
                    .reserveEnd(timeSlot.getReserveEnd())
                    .personCnt(requestDto.getPersonCnt())
                    .status("A")
                    .reserveDate(requestDto.getReserveDate())
                    .build();

            try {
                reservationMapper.createReservation(saveReservation);
                int id = reservationMapper.getReservationId(saveReservation.getUserId(),saveReservation.getReserveDate(), saveReservation.getReserveStart());
                ids.add(id);
            } catch (Exception e) {
                log.info(e.getMessage());
                throw new GeneralException(CustomResponseCode.CREATE_RESERVATION_FAILED);
            }
        }
        ReservationDto.UserReservationResponseDto response = new ReservationDto.UserReservationResponseDto(ids);
        return response;
    }

    @Override
    public List<ReservationDto.TimeSlotResponseDto> getAvailableTimeSlots(String reserveDate, int tableId) {

        // tableId로 cafeId 검색
        CafeTable cafeTable = cafeTableMapper.getOneCafeTable(tableId);
        if(cafeTable == null){
            throw new GeneralException(CustomResponseCode.CAFETABLE_NOT_FOUND);
        }
        int cafeId = cafeTable.getCafeId();

        // 검색한 cafeId로 카페 오픈,마감시간 가져오기
        Cafe cafe = cafeMapper.getOneCafe(cafeId);
        CafeDto.CafeTimeResponseDto cafeTimeResponseDto = CafeDto.CafeTimeResponseDto.builder()
                .startTime(cafe.getStartTime())
                .endTime(cafe.getEndTime())
                .build();

        log.info(String.valueOf(cafeTimeResponseDto));

        // 오픈시간, 마감시간 형식 변환
        String startTime = cafeTimeResponseDto.getStartTime();
        int startHour = Integer.parseInt(startTime.split(":")[0]);
        String endTime = cafeTimeResponseDto.getEndTime();
        int endHour = Integer.parseInt(endTime.split(":")[0]);

        log.info("시작시간 "+String.valueOf(startTime));
        log.info("마감시간 "+String.valueOf(endTime));
        log.info("변환시작시간 "+String.valueOf(startHour));
        log.info("변환마감시간 "+String.valueOf(endHour));

        // 오픈시간부터 마감시간까지의 timeslot 만들기(default avilable이 Y)
        List<ReservationDto.TimeSlotResponseDto> newTimeslot = new LinkedList<>();
        for(int i=startHour; i<endHour; i++){
            String start = String.format("%02d:00", i); // 두 자리 정수로 포맷팅
            String end = String.format("%02d:00", i + 1);

            ReservationDto.TimeSlotResponseDto timeslot = ReservationDto.TimeSlotResponseDto.builder()
                    .reserveStart(start)
                    .reserveEnd(end)
                    .available("Y")
                    .build();

            newTimeslot.add(timeslot);
        }

        log.info(String.valueOf(newTimeslot));

        // date 형식 변환
        String formatDate = reserveDate.substring(0, 4) + "-" + reserveDate.substring(4, 6) + "-" + reserveDate.substring(6);
        log.info("formatDate"+formatDate);

        // 예약날짜와 tableId로 예약 내역 가져오기
        List<Reservation> reservations = reservationMapper.getOneTableRev(formatDate, tableId);
        log.info(String.valueOf(reservations));

        if(reservations == null){
            return  newTimeslot;
        }

        for (Reservation reservation : reservations) {
            String reserveStartTime = reservation.getReserveStart();

            for (ReservationDto.TimeSlotResponseDto timeSlot : newTimeslot) {
                String timeSlotStartTime = timeSlot.getReserveStart();

                if (reserveStartTime.equals(timeSlotStartTime)) {
                    timeSlot.setAvailable("N");
                }
            }
        }

        log.info(newTimeslot.toString());


        return newTimeslot;

    }

    @Override
    public ReservationDto.RevCafeInfoResponseDto getRevCafeInfo(int cafeId) {
        log.info("시작했음");

        Cafe cafe = cafeMapper.getOneCafe(cafeId);

        if (cafe == null) {
            throw new GeneralException(CustomResponseCode.CAFE_NOT_FOUND);
        }

        if(cafe.getStudy().equals("N")){
            throw new GeneralException(CustomResponseCode.NO_RESERVATION_CAFE);
        }

        String img = Base64.getEncoder().encodeToString(cafe.getStudyImg());

        Map<String, List<CafeTableDto.CafeTableInfoResponseDto>> tableInfo = cafeTableService.getTableInfo(cafeId);

        ReservationDto.RevCafeInfoResponseDto revCafeInfoResDto = ReservationDto.RevCafeInfoResponseDto.builder()
                .cafeName(cafe.getCafeName())
                .tableInfo(tableInfo)
                .studyImg(img)
                .studyImgMine(cafe.getStudyImgMine())
                .build();

        log.info(String.valueOf(revCafeInfoResDto));
        return revCafeInfoResDto;
    }

    @Override
    public List<ReservationDto.ManagerReservationResponseDto> getDateReservation(String date, String userName) {

        // 날짜 계산
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate inputDate = LocalDate.parse(date, formatter);

        LocalDate today = LocalDate.now();
        LocalDate before = today.minusDays(30);
        LocalDate after = today.plusDays(7);

        if (inputDate.isBefore(before) || inputDate.isAfter(after)) {
            throw new GeneralException(CustomResponseCode.NO_CHECK_DATE);
        }


        int cafeId = getCafIdByUsername(userName);
        //int cafeId = 22; // 점주 1명에 여러 카페 등록이라 임시

        // 카페의 해당 날짜의 전체 예약 불러오기
        String formatDate = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
        List<Reservation> allReservation = reservationMapper.getOneCafeOneDayRev(formatDate, cafeId);
        //log.info(allReservation.toString());

        List<ReservationDto.ManagerReservationResponseDto> responseList = combinedTimeReservations(allReservation);

        //log.info(responseList.toString());

        return responseList;
    }

    @Override
    public List<ReservationDto.ManagerReservationResponseDto> getBeforeReservation(String userName) {

        int cafeId = getCafIdByUsername(userName);
        //int cafeId = 22; // 점주 1명에 여러 카페 등록이라 임시

        // 상태가 A(신청)인 예약 가져오기
        List<Reservation> afterReservation = reservationMapper.getOneCafeBeforeRev(cafeId);

        List<ReservationDto.ManagerReservationResponseDto> responseList = combinedTimeReservations(afterReservation);

        return responseList;
    }

    @Override
    public List<ReservationDto.ManagerReservationResponseDto> getIngReservation(String userName) {

        int cafeId = getCafIdByUsername(userName);
        // int cafeId = 22; // 점주 1명에 여러 카페 등록이라 임시

        // 상태가 P(진행중)인 예약 가져오기
        List<Reservation> afterReservation = reservationMapper.getOneCafeIngRev(cafeId);

        List<ReservationDto.ManagerReservationResponseDto> responseList = combinedTimeReservations(afterReservation);

        return responseList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changeConfirmReservation(ReservationDto.ConfAndFinReservationRequestDto requestDto, String userName) {

        int cafeId = getCafIdByUsername(userName);
        //int cafeId = 22; // 점주 1명에 여러 카페 등록이라 임시

        List<Integer> reservationIds = requestDto.getReservationIds();

        for (Integer reservationId : reservationIds) {
            Reservation temp = reservationMapper.getRevByRevId(reservationId);

            if(temp == null){
                throw new GeneralException(CustomResponseCode.NO_RESERVATION);
            }

            if(temp.getCafeId() != cafeId){
                throw new GeneralException(CustomResponseCode.NO_CAFE_MANAGER);
            }

            try{
                reservationMapper.cofirmReservation(reservationId);
                log.info(reservationId+"가 변경됨");
            } catch (Exception e) {
                log.info(e.getMessage());
                throw new GeneralException(CustomResponseCode.COFIRM_RESERVATION_FAILED);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changeCancleReservation(ReservationDto.CancleReservationRequestDto requestDto, String userName) {

        int cafeId = getCafIdByUsername(userName);
        //int cafeId = 22; // 점주 1명에 여러 카페 등록이라 임시

        List<Integer> reservationIds = requestDto.getReservationIds();

        String cancleReasonId = requestDto.getCancleReasonId();
        CancleReason cancleReason = cancleReasonMapper.getOneCancleReason(cancleReasonId);

        if (cancleReason == null) {
            throw new GeneralException(CustomResponseCode.NO_CANCLEREASON);
        }
        for (Integer reservationId : reservationIds) {
            Reservation temp = reservationMapper.getRevByRevId(reservationId);
            if(temp == null){
                throw new GeneralException(CustomResponseCode.NO_RESERVATION);
            }

            if(temp.getCafeId() != cafeId){
                throw new GeneralException(CustomResponseCode.NO_CAFE_MANAGER);
            }

            try{
                reservationMapper.cancleReservation(reservationId, cancleReasonId);
                log.info(reservationId+"가 변경됨");
            } catch (Exception e) {
                log.info(e.getMessage());
                throw new GeneralException(CustomResponseCode.CANCLE_RESERVATION_FAILED);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changeFinishReservation(ReservationDto.ConfAndFinReservationRequestDto requestDto, String userName) {

        int cafeId = getCafIdByUsername(userName);
        //int cafeId = 22; // 점주 1명에 여러 카페 등록이라 임시

        List<Integer> reservationIds = requestDto.getReservationIds();

        for (Integer reservationId : reservationIds) {
            Reservation temp = reservationMapper.getRevByRevId(reservationId);

            if(temp == null){
                throw new GeneralException(CustomResponseCode.NO_RESERVATION);
            }

            if(temp.getCafeId() != cafeId){
                throw new GeneralException(CustomResponseCode.NO_CAFE_MANAGER);
            }

            try{
                reservationMapper.finishReservation(reservationId);
                log.info(reservationId+"가 변경됨");
            } catch (Exception e) {
                log.info(e.getMessage());
                throw new GeneralException(CustomResponseCode.COFIRM_RESERVATION_FAILED);
            }
        }
        return true;
    }

    public List<ReservationDto.UserReadFinishReservResponseDto> finishReservations(String userName) {
        Users user = usersMapper.getOneUsers(userName);
        if(user == null){
            throw new GeneralException(CustomResponseCode.USER_NOT_FOUND);
        }
        Cafe cafe = cafeMapper.getOneCafeByUserId(user.getUserId());
        List<Reservation> reservations = reservationMapper.getFinReservations(user.getUserId());

        return combinedTimeFinishReservations(reservations);
    }

    @Override
    public List<ReservationDto.UserReadFinishReservResponseDto> proceedReservations(String userName) {
        Users user = usersMapper.getOneUsers(userName);
        if(user == null){
            throw new GeneralException(CustomResponseCode.USER_NOT_FOUND);
        }
        Cafe cafe = cafeMapper.getOneCafeByUserId(user.getUserId());
        List<Reservation> reservations = reservationMapper.getProceedReservations(user.getUserId());

        return combinedTimeFinishReservations(reservations);

    }

    /**
     * 실시간 예약 현황 상태 확인
     */
    @Override
    public ReservationDto.UserReservationStatusResponseDto reservationStatus(String userName, int reservationId) {
        Users user = usersMapper.getOneUsers(userName);

        log.info("reservationId"+reservationId);
        Reservation reservation = reservationMapper.getRevByRevId(reservationId);
        log.info(reservation.getStatus());
        return new ReservationDto.UserReservationStatusResponseDto(user.getUserRealName(), reservation.getStatus(), reservationId);
    }

    @Override
    public ReservationDto.CancleReasonResponDto cancleReason(int reservationId, String userName) {
        CancleReason cancleReason = cancleReasonMapper.getReservationCancleReason(reservationId);
        Users user = usersMapper.getOneUsers(userName);
        Reservation reservation = reservationMapper.getRevByRevId(reservationId);
        int cafeId = reservation.getCafeId();
        Cafe cafe = cafeMapper.getOneCafe(cafeId);

        return new ReservationDto.CancleReasonResponDto(reservationId, cancleReason.getCancleContent(),cafe.getCafeTel() ,user.getUserRealName());
    }

    // 토큰 값으로 cafeId 가져오기
    public int getCafIdByUsername (String userName){

        Users manager = usersMapper.getOneUsers(userName);
        if(manager == null){
            throw new GeneralException(CustomResponseCode.USER_NOT_FOUND);
        }

        return cafeImgService.findCafeIdByUserName(userName);
    }

    // reservation -> DateReservationResponseDto 변경
    public ReservationDto.ManagerReservationResponseDto convertReservationToDto(Reservation reservation, List<Integer> reservationIds) {

        Users users = usersMapper.getUserByUserId(reservation.getUserId());
        String userRealName = users.getUserRealName();

        CafeTable cafeTable = cafeTableMapper.getOneCafeTable(reservation.getTableId());
        String tableNumber = cafeTable.getTableNumber();
        String tableType = cafeTable.getTableType();

        return ReservationDto.ManagerReservationResponseDto.builder()
                .reservationIds(reservationIds)
                .userRealName(userRealName)
                .tableNumber(tableNumber)
                .tableType(tableType)
                .personCnt(reservation.getPersonCnt())
                .reserveStart(reservation.getReserveStart())
                .reserveEnd(reservation.getReserveEnd())
                .reserveDate(reservation.getReserveDate())
                .status(reservation.getStatus())
                .build();
    }

    // 주어진 List<reservation> 시간 연결하기
    public List<ReservationDto.ManagerReservationResponseDto> combinedTimeReservations(List<Reservation> reservations) {
        List<ReservationDto.ManagerReservationResponseDto> responseList = new ArrayList<>();

        if (!reservations.isEmpty()) {
            Reservation current = reservations.get(0);
            List<Integer> reservationIds = new ArrayList<>();
            reservationIds.add(current.getReservationId());


            for (int i = 1; i < reservations.size(); i++) {
                Reservation next = reservations.get(i);

                if ((current.getUserId() == next.getUserId())
                        && (current.getTableId() == next.getTableId())
                        && (current.getReserveEnd().equals(next.getReserveStart()))) {
                    current.setReserveEnd(next.getReserveEnd());
                    reservationIds.add(next.getReservationId());
                } else {
                    responseList.add(convertReservationToDto(current, reservationIds));
                    current = next;
                    reservationIds = new ArrayList<>();
                    reservationIds.add(current.getReservationId());
                }
            }
            responseList.add(convertReservationToDto(current, reservationIds));
        }

        return responseList;
    }

    public List<ReservationDto.UserReadFinishReservResponseDto> combinedTimeFinishReservations(List<Reservation> reservations) {
        List<ReservationDto.UserReadFinishReservResponseDto> responseList = new ArrayList<>();

        if (!reservations.isEmpty()) {
            Reservation current = reservations.get(0);
            List<Integer> reservationIds = new ArrayList<>();
            reservationIds.add(current.getReservationId());


            for (int i = 1; i < reservations.size(); i++) {
                Reservation next = reservations.get(i);

                if ((current.getUserId() == next.getUserId())
                        && (current.getTableId() == next.getTableId())
                        && (current.getReserveEnd().equals(next.getReserveStart()))
                        && current.getReserveDate().equals(next.getReserveDate())) {
                    current.setReserveEnd(next.getReserveEnd());
                    reservationIds.add(next.getReservationId());
                } else {
                    responseList.add(convertFinishReservationToDto(current, reservationIds));
                    current = next;
                    reservationIds = new ArrayList<>();
                    reservationIds.add(current.getReservationId());
                }
            }
            responseList.add(convertFinishReservationToDto(current, reservationIds));
        }

        return responseList;
    }

    /**
     * 만료된 유저 예약 리스트 조회
     */
    public ReservationDto.UserReadFinishReservResponseDto convertFinishReservationToDto(Reservation reservation, List<Integer> reservationIds) {

        CafeTable cafeTable = cafeTableMapper.getOneCafeTable(reservation.getTableId());
        String tableNumber = cafeTable.getTableNumber();
        Cafe cafe = cafeMapper.getOneCafe(reservation.getCafeId());
        String cafeRepImg = Base64.getEncoder().encodeToString(cafe.getCafeRepImg());

        return new ReservationDto.UserReadFinishReservResponseDto(reservation,reservationIds,tableNumber,cafe.getCafeName(), cafeRepImg);
    }

}
