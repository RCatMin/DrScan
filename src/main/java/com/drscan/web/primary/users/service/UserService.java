package com.drscan.web.primary.users.service;

import com.drscan.web.primary.users.domain.User;
import com.drscan.web.primary.users.domain.UserRepository;
import com.drscan.web.primary.users.domain.UserRequestDto;
import jakarta.transaction.Transactional;
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

    public List<User> findAllByAccountType(String accountType) {
        return userRepository.findAllByAccountType(accountType);
    }

    public List<User> findAllByAccountTypeAndStatus(String accountType, String status) {
        return userRepository.findAllByAccountTypeAndStatus(accountType, status);
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

    @Transactional
    public boolean updateUser(UserRequestDto userDto) {
        Integer code = Integer.valueOf(userDto.getCode());

        User target = userRepository.findUserByUserCode(code);

        if(target == null)
            return false;

        target.update(userDto);
        return true;
    }

    @Transactional
    public boolean approveUser(UserRequestDto userDto) {
        Integer code = Integer.valueOf(userDto.getCode());
        User target = userRepository.findUserByUserCode(code);

        String accountType = target.getAccountType();
        String status = target.getStatus();

        if(accountType.equals("temporary") && status.equals("pending")) {
            target.approve("user", "active");
        } else if(accountType.equals("user") && status.equals("pending")) {
            userRepository.delete(target);
        }

        if(target == null)
            return false;

        return true;
    }

    public void incrementFailCountAndCheckSuspension(UserRequestDto userDto) {
        User target = userRepository.findUserByUsername(userDto.getUsername());

        if (target != null) {
            target.incrementFailCountAndSuspended();
            userRepository.save(target);
        }
    }

    public void resetFailCount(UserRequestDto userDto) {
        User target = userRepository.findUserByUsername(userDto.getUsername());

        if (target != null) {
            target.resetFailCount();
            userRepository.save(target);
        }
    }
}