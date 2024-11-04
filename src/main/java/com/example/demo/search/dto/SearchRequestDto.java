package com.example.demo.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestDto {
    private String cafeType;
    private String studyEnable;
    private String people; //다인석
    private String ing;  //운영중
    private List<String> features = new ArrayList<>();  //특성
    private String startTime;
    private String endTime;
    private String userStudy;
    private String preferSeat;
}
