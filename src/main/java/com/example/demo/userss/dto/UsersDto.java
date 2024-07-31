package com.example.demo.userss.dto;

import lombok.*;

public class UsersDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserSignupRequestDto{
        private String userName;
        private String userRealName;
        private String password;
        private String role;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserSignupResponseDto{
        private String userName;
    }

    @Data
    @AllArgsConstructor
    @Getter
    public static class UserLoginRequestDto{
        private String userName;
        private String password;
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLoginResponseDto{
        private String userName;
        private String role;
        private String accessToken;
    }

    @Getter
    public static class UserCheckPwRequestDto{
        private String password;
    }
}
