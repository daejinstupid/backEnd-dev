package com.example.demo.userss.entity;

import com.example.demo.userss.dto.UsersDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users implements UserDetails {


    private int userId;
    private String userName;
    private String password;
    private String userRealName;
    private String role;
    private Date createDate;
    private Date modifyDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public Users(UsersDto.UserSignupRequestDto signup, String password, String role){
        this.userName = signup.getUserName();
        this.password = password;
        this.userRealName = signup.getUserRealName();
        this.role = role;
    }
}
