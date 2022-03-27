package com.example.demospringsecurity.service;

import com.example.demospringsecurity.entity.User;
import com.example.demospringsecurity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void register(String name, String email, String password, String[] roles){
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("invalid name or email");
        }
        //暗号化する
        String encodedPassword = passwordEncode(password);
        String joinedRoles = joinRoles(roles);
        log.debug("name:{}, email:{}, roles:{}", name, email, joinedRoles);
        User user = new User(null, name, email, encodedPassword, joinedRoles, Boolean.TRUE);
        userRepository.saveAndFlush(user);
    }

    private String passwordEncode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    private String joinRoles(String[] roles) {
        if (roles == null || roles.length == 0) {
            return "";
        }
        return Stream.of(roles)
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.joining(","));
    }
}
