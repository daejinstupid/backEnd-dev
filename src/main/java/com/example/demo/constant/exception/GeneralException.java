package com.example.demo.constant.exception;

import com.example.demo.constant.enums.CustomResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GeneralException extends RuntimeException{

    CustomResponseCode code;
}
