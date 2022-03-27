package com.example.demospringsecurity.auth;

import com.example.demospringsecurity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class simpleUserDetailsService {
    private final UserRepository userRepository;

    public simpleUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException {
        assert (email != null);
        log.debug("loadUserByUsername(email):[{}]", email);
        return userRepository.findByEmail(email)
                .map(SimpleLoginUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by email:[" + email + "]"));
    }
}
