package com.example.demo.reservation.cancelReason.service;

import com.example.demo.reservation.cancelReason.dto.CancelReasonDto;
import com.example.demo.reservation.cancelReason.mapper.CancelReasonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancelReasonService {

    @Autowired
    private CancelReasonMapper cancelReasonMapper;

    public List<CancelReasonDto> getCancelReasonStatsByCafeId(int cafeId) {
        return cancelReasonMapper.getCancelReasonStatsByCafeId(cafeId);
    }
}
