package com.example.demo.search.service.Impl;

import com.example.demo.cafe.dto.CafeDto;
import com.example.demo.cafe.dto.PageDto;
import com.example.demo.cafe.entity.Cafe;
import com.example.demo.search.dto.SearchDto;
import com.example.demo.search.dto.SearchRequestDto;
import com.example.demo.search.mapper.SearchMapper;
import com.example.demo.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchServiceImpl implements SearchService {
    private final SearchMapper searchMapper;

    @Override
    public SearchDto.SearchResponseDto search(SearchDto.SearchRequestDto searchRequestDto,int pageNo) {
        log.info("searchRequestDto: "+ searchRequestDto);

        int cafeListSize = searchMapper.search(searchRequestDto);

        PageDto pager = new PageDto(5,5,cafeListSize,pageNo);
        List<Cafe> cafeList = searchMapper.searchByPage(searchRequestDto, pager);


        List<CafeDto.CafeSearchResponseDto> newList = new ArrayList<>();

        for(Cafe c : cafeList){
            CafeDto.CafeSearchResponseDto cafedto = null;
            Double distance = null;
            if(searchMapper.findRepImg(c.getCafeId()).getCafeRepImg() == null){
                cafedto = new CafeDto.CafeSearchResponseDto(c, null);

            }
            else {
                c.setCafeRepImg(searchMapper.findRepImg(c.getCafeId()).getCafeRepImg());
                cafedto = new CafeDto.CafeSearchResponseDto(c, Base64.getEncoder().encodeToString(c.getCafeRepImg()));
            }
            cafedto.setDistance(6371 * Math.acos(Math.cos(searchRequestDto.getLatitude()/57.29577951)
                    * Math.cos(cafedto.getLatitude()/57.29577951)
                    * Math.cos(c.getLongitude()/57.29577951 - searchRequestDto.getLongitude()/57.29577951)
                    + Math.sin(searchRequestDto.getLatitude()/57.29577951) * Math.sin(c.getLatitude()/57.29577951)));
            newList.add(cafedto);
        }

        newList = newList.stream()
                .sorted(Comparator.comparing(CafeDto.CafeSearchResponseDto::getDistance))
                .collect(Collectors.toList());


        return new SearchDto.SearchResponseDto(newList,pager);
    }

    @Override
    public SearchDto.SearchResponseDto searchWord(String word,int pageNo) {

        int count = searchMapper.searchWordCount(word);
        PageDto pager = new PageDto(5,5,count,pageNo);
        List<Cafe> wordsCafe = searchMapper.searchWord(word,pager);
        //임시//
        List<CafeDto.CafeSearchResponseDto> newList = new ArrayList<>();

        for(Cafe c : wordsCafe){
            CafeDto.CafeSearchResponseDto cafedto = null;

            if(searchMapper.findRepImg(c.getCafeId()).getCafeRepImg() == null){
                cafedto = new CafeDto.CafeSearchResponseDto(c, null);

            }
            else {
                c.setCafeRepImg(searchMapper.findRepImg(c.getCafeId()).getCafeRepImg());
                cafedto = new CafeDto.CafeSearchResponseDto(c, Base64.getEncoder().encodeToString(c.getCafeRepImg()));

            }
            newList.add(cafedto);

        }
        return new SearchDto.SearchResponseDto(newList,pager);
    }

    @Override
    public SearchDto.SearchResponseDto searchByMyLocation(Double longitude, Double latitude,int pageNo) {
        int count = searchMapper.searchByMyLocationCount(longitude, latitude);
        PageDto pager = new PageDto(5, 5, count, pageNo);
        List<Cafe> cafes = searchMapper.searchByMyLocation(longitude, latitude, pager);
        //임시//
        List<CafeDto.CafeSearchResponseDto> newList = new ArrayList<>();

        for (Cafe c : cafes) {
            CafeDto.CafeSearchResponseDto cafedto = null;

            if (searchMapper.findRepImg(c.getCafeId()).getCafeRepImg() == null) {
                cafedto = new CafeDto.CafeSearchResponseDto(c, null);

            } else {
                c.setCafeRepImg(searchMapper.findRepImg(c.getCafeId()).getCafeRepImg());
                cafedto = new CafeDto.CafeSearchResponseDto(c, Base64.getEncoder().encodeToString(c.getCafeRepImg()));

            }
            newList.add(cafedto);

        }
        return new SearchDto.SearchResponseDto(newList, pager);
    }

}
