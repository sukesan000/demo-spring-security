package com.example.demospringsecurity.auth;

import com.example.demospringsecurity.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class SimpleLoginUser extends org.springframework.security.core.userdetails.User{
    private User user;

    public SimpleLoginUser(User user){
        super(user.getEmail(), user.getPassword(), user.getEnable_flag(), true, true,
                true, convertGrantedAuthorities(user.getRoles()));
        this.user = user;

    }

    public  User getUser(){
        return user;
    }

    static Set<GrantedAuthority> convertGrantedAuthorities(String roles){
        if(roles == null || roles.isEmpty()){
            return Collections.emptySet();
        }

        Set<GrantedAuthority> authorities = Stream.of(roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        return authorities;
    }
}
