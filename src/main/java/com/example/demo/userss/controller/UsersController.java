package com.example.demo.userss.controller;

import com.example.demo.constant.dto.ApiResponse;
import com.example.demo.constant.enums.CustomResponseCode;
import com.example.demo.userss.dto.UsersDto;
import com.example.demo.userss.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UsersService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UsersDto.UserSignupResponseDto>> signup(@RequestBody UsersDto.UserSignupRequestDto userSignupRequestDto){
        UsersDto.UserSignupResponseDto userSignupResponseDto = userService.signup(userSignupRequestDto);
        log.info(userSignupResponseDto.getUserName());
        log.info("apiresponse: "+ApiResponse.createSuccess(userSignupResponseDto, CustomResponseCode.SUCCESS));
        return ResponseEntity.ok().body(ApiResponse.createSuccess(userSignupResponseDto, CustomResponseCode.SUCCESS));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UsersDto.UserLoginResponseDto>> login(@RequestBody UsersDto.UserLoginRequestDto userLoginRequestDto){
        log.info("들어옴!");
        UsersDto.UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);
        log.info(userLoginResponseDto.toString());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(userLoginResponseDto, CustomResponseCode.SUCCESS));
    }

    /**
     * true: 중복 없음
     * false: 중복
     */
    @GetMapping("/check/userName/dup/{userName}")
    public ResponseEntity<ApiResponse<Boolean>> checkUserName(@PathVariable String userName){
        Boolean result = userService.checkUserNameDup(userName);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(result,CustomResponseCode.SUCCESS));
    }

    @PostMapping("/user/check/password")
    public ResponseEntity<ApiResponse<Boolean>> checkUserPw(@RequestBody UsersDto.UserCheckPwRequestDto userCheckPwRequestDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Boolean check = userService.validatePw(userDetails, userCheckPwRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(check, CustomResponseCode.SUCCESS));
    }

    /**
     * 비밀번호 변경
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PatchMapping("/user/update/password")
    public ResponseEntity<ApiResponse<String>> updatePw(
            @RequestBody UsersDto.UserCheckPwRequestDto updatePwRequestDto,
            Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userService.updatePassword(userDetails.getUsername(), updatePwRequestDto);

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseCode.SUCCESS));
    }

    @PostMapping("/user/logout")
    public ResponseEntity<ApiResponse<String>> logout(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userService.logout(userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseCode.SUCCESS));
    }
}
