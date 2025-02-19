package com.drscan.web.users.service;

import com.drscan.web.users.domain.User;
import com.drscan.web.users.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    public final UserRepository userRepository;

    public List<User> findUserAll(){
        return userRepository.findAll();
    }
}
