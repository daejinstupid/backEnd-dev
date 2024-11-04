package com.example.demo.cafeTable.mapper;

import com.example.demo.cafeTable.dto.CafeTableDto;
import com.example.demo.cafeTable.entity.CafeTable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CafeTableMapper {
    CafeTable getOneCafeTable(int tableId);
    List<CafeTableDto.CafeTableInfoResponseDto> getTableInfo(int cafeId, String tableType);

    void deleteCafeTableOne(int tableId);

    CafeTable getTableInfoOne(int cafeId);

    void insertCafeTable(CafeTableDto.CafeTableInsertRequestDto requestDto);
}
