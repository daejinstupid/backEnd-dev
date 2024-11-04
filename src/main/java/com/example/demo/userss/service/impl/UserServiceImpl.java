package com.example.demo.userss.service.impl;

import com.example.demo.constant.enums.CustomResponseCode;
import com.example.demo.constant.exception.GeneralException;
import com.example.demo.security.service.JwtUtils;
import com.example.demo.userss.dto.UsersDto;
import com.example.demo.userss.entity.Users;
import com.example.demo.userss.mapper.UsersMapper;
import com.example.demo.userss.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UsersService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;
    private final UsersMapper usersMapper;

    @Override
    public UsersDto.UserLoginResponseDto login(UsersDto.UserLoginRequestDto userLoginRequestDto) {
        log.info("service 들어옴");
        Users user = usersMapper.getOneUsers(userLoginRequestDto.getUserName());
        if(user == null){
            throw new GeneralException(CustomResponseCode.USER_NOT_FOUND);
        }

        log.info("login서비스 usermapper가 찾은 user: "+user);


        if(!passwordEncoder.matches(userLoginRequestDto.getPassword(),user.getPassword())){
            log.info("userLoginRequestDto.getPassword(): "+ userLoginRequestDto.getPassword());
            throw new GeneralException(CustomResponseCode.NOT_EQUALS_PASSWORD);
        }

        String accessToken = jwtUtils.createToken(userLoginRequestDto.getUserName());
        log.info("accesstoken: "+ accessToken);
        log.info("accessToken: "+accessToken);
        return UsersDto.UserLoginResponseDto.builder()
                .userName(userLoginRequestDto.getUserName())
                .role(user.getRole())
                .accessToken(accessToken)
                .build();
    }

    @Override
    public UsersDto.UserSignupResponseDto signup(UsersDto.UserSignupRequestDto userSignupRequestDto) {
        String role ="";
        if(userSignupRequestDto.getRole().equals("USER")){
            role = "ROLE_USER";
        }
        else{
            role = "ROLE_MANAGER";
        }
        String password = passwordEncoder.encode(userSignupRequestDto.getPassword());

        Users user = new Users(userSignupRequestDto,password, role);
        usersMapper.signupUsers(user);
        log.info(user.toString());
        return UsersDto.UserSignupResponseDto.builder()
                .userName(user.getUsername())
                .build();
    }

    @Override
    public Boolean checkUserNameDup(String userName) {
        Boolean result = false;
        Users users = usersMapper.getOneUsers(userName);
        if(users == null){
            result = true;
        }
        return result;
    }

    @Override
    public Boolean validatePw(UserDetails userDetails, UsersDto.UserCheckPwRequestDto userCheckPwRequestDto) {
        if (passwordEncoder.matches(userCheckPwRequestDto.getPassword(), userDetails.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public void updatePassword(String userName, UsersDto.UserCheckPwRequestDto userPwUpdateRequestDto) {
        String password = passwordEncoder.encode(userPwUpdateRequestDto.getPassword());
        usersMapper.usersPwUpdate(userName,password);

    }

    @Override
    public void logout(String userName) {

    }

}
