package com.example.demo.reservation.cancelReason.controller;

import com.example.demo.reservation.cancelReason.dto.CancelReasonDto;
import com.example.demo.reservation.cancelReason.service.CancelReasonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CancelReasonController {

    @Autowired
    private CancelReasonService cancelReasonService;

    @GetMapping("manager/cancelreservationstats/{cafeId}")
    public ResponseEntity<List<CancelReasonDto>> getCancelReasonStatsByCafeId(@PathVariable int cafeId) {
        List<CancelReasonDto> stats = cancelReasonService.getCancelReasonStatsByCafeId(cafeId);
        return ResponseEntity.ok(stats);
    }

}
