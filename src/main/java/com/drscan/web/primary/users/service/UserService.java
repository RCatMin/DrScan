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

    public String createUser(User user) {
        User existUserByUsername = userRepository.findUserByUsername(user.getUsername());
        User existUserByEmail = userRepository.findUserByEmail(user.getEmail());
        User existUserByPhone = userRepository.findUserByPhone(user.getPhone());

        if(existUserByUsername != null){
            return "아이디가 중복됩니다.";
        } else if(existUserByEmail != null){
            return "이메일이 중복됩니다.";
        } else if(existUserByPhone != null){
            return "전화번호가 중복됩니다.";
        }

        userRepository.save(user);
        return "success";
    }
}
