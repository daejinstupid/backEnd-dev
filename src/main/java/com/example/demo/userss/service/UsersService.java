package com.example.demo.userss.service;

import com.example.demo.userss.dto.UsersDto;
import com.example.demo.userss.entity.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


public interface UsersService {

    UsersDto.UserLoginResponseDto login(UsersDto.UserLoginRequestDto userLoginRequestDto);

    UsersDto.UserSignupResponseDto signup(UsersDto.UserSignupRequestDto userSignupRequestDto);

    Boolean checkUserNameDup(String userName);

    Boolean validatePw(UserDetails userDetails, UsersDto.UserCheckPwRequestDto userCheckPwRequestDto);

    void updatePassword(String userName, UsersDto.UserCheckPwRequestDto userPwUpdateRequestDto);

    void logout(String userName);

}
