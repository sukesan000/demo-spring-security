package com.example.demospringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // アカウント登録時のパスワードエンコードで利用するためDI管理する。
    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web)throws Exception{
        //制限を適用しないリソースを設定
        web.debug(false)
                .ignoring()
                .antMatchers("/images/**", "/js/**", "/css/**");
    }

    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                //全てのユーザがアクセス可
                .mvcMatchers("/", "/signup").permitAll()
                //userのみアクセス可
                .mvcMatchers("/members/user/**").hasRole("USER")
                //userのみアクセス可
                .mvcMatchers("/members/admin/**").hasRole("ADMIN")
                // 上記以外のページは、認証ユーザーがアクセスできる
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //ログイン成功時に遷移
                .defaultSuccessUrl("/")
                .and()
                .logout()
                // ログアウト時にセッション破棄
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");
    }
}
