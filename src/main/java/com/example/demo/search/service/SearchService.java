package com.example.demo.search.service;

import com.example.demo.search.dto.SearchDto;
import com.example.demo.search.dto.SearchRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface SearchService {
    SearchDto.SearchResponseDto search(SearchDto.SearchRequestDto searchRequestDto,int pageNo);

    SearchDto.SearchResponseDto searchWord(String word,int pageNo);
    SearchDto.SearchResponseDto searchByMyLocation(Double longitude, Double latitude,int pageNo);
}
