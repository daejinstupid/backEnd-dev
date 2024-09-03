package com.example.demo.search.controller;

import com.example.demo.constant.dto.ApiResponse;
import com.example.demo.constant.enums.CustomResponseCode;
import com.example.demo.search.dto.SearchDto;
import com.example.demo.search.dto.SearchRequestDto;
import com.example.demo.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final SearchService searchService;
    @GetMapping("/user/search")
    public ResponseEntity<ApiResponse<SearchDto.SearchResponseDto>> search(
            @RequestParam(value = "cafeType", required = false) String cafeType,
            @RequestParam(value = "studyEnable",required = false) String studyEnable,
            @RequestParam(value = "people",required = false) String people,
            @RequestParam(value = "proceed",required = false) String proceed,
            @RequestParam(value = "features",required = false) List<String> features,
            @RequestParam(value = "startTime",required = false) String startTime,
            @RequestParam(value = "endTime",required = false) String endTime,
            @RequestParam(value = "preferSeat",required = false) String preferSeat,
            @RequestParam(value = "word",required = false)String word,
            @RequestParam(value = "longtitude") Double longtitude,
            @RequestParam(value = "latitude") Double latitude,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo
    ){
        SearchDto.SearchRequestDto searchRequestDto = new SearchDto.SearchRequestDto(
                cafeType,studyEnable,people,proceed,features,startTime,endTime,preferSeat,word,longtitude,latitude
        );
        log.info(searchRequestDto.toString());
        log.info(String.valueOf(pageNo));
        SearchDto.SearchResponseDto searchResponseDto = searchService.search(searchRequestDto,pageNo);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(searchResponseDto, CustomResponseCode.SUCCESS));
    }

    @GetMapping("/user/search/relative/{word}")
    public ResponseEntity<ApiResponse<SearchDto.SearchResponseDto>> searchWord(
            @PathVariable String word,
            @RequestParam(defaultValue = "1") int pageNo){
        log.info(word);
        SearchDto.SearchResponseDto words = searchService.searchWord(word,pageNo);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(words,CustomResponseCode.SUCCESS));
    }

    @GetMapping("/user/search/near/mylocation")
    public ResponseEntity<ApiResponse<SearchDto.SearchResponseDto>> searchByMyLocation(
            @RequestParam(value = "x") Double longtitude,
            @RequestParam(value = "y") Double latitude,
            @RequestParam(value ="pageNo",defaultValue = "1") int pageNo,
            Authentication authentication
    ){
        SearchDto.SearchResponseDto nearCafesMyLocation = searchService.searchByMyLocation(longtitude,latitude,pageNo);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(nearCafesMyLocation, CustomResponseCode.SUCCESS));
    }
}
