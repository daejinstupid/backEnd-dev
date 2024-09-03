package com.example.demo.cafeTable.service;

import com.example.demo.cafeTable.dto.CafeTableDto;

import java.util.List;
import java.util.Map;

public interface CafeTableService {
    Map<String, List<CafeTableDto.CafeTableInfoResponseDto>> getTableInfo(int cafeId);

    void insertCafeTable(CafeTableDto.CafeTableInsertRequestDto requestDto, String userName);

    CafeTableDto.CafeTableResponseDto getTableInfoOne(String userName);

    void deleteCafeTableOne(int tableId);
}
