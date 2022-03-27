package com.example.demospringsecurity.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupForm {
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String name;
}
