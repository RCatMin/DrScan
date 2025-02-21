package com.drscan.web.primary.users.service;

import com.drscan.web.primary.users.domain.User;
import com.drscan.web.primary.users.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findUserAll(){ return userRepository.findAll(); }

    public User findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }
}
