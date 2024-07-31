package com.example.demo.constant.dto;

import com.example.demo.constant.enums.CustomResponseCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonPropertyOrder({"isSuccess","code","message","data"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private Boolean isSuccess;
    private int code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static ApiResponse<String> createSuccessWithNoContent(CustomResponseCode code){
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), null);
    }

    public static <T> ApiResponse<T> createSuccess(T data, CustomResponseCode code){
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), data);
    }

    public static ApiResponse<String> createError(CustomResponseCode code){
        return new ApiResponse<>(code.isSuccess(), code.getCode(), code.getMessage(),null);
    }

}
