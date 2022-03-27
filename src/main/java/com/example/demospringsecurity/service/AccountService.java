package com.example.demospringsecurity.service;

import com.example.demospringsecurity.entity.User;

import java.util.List;

public interface AccountService {
    List<User> findAll();
    void register(String name, String email, String password, String[] roles);
}
