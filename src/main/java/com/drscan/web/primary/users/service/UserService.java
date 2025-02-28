package com.drscan.web.primary.users.service;

import com.drscan.web.primary.users.domain.User;
import com.drscan.web.primary.users.domain.UserRepository;
import com.drscan.web.primary.users.domain.UserRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    public boolean existsByUsernameAndUserCodeNot(String username, Integer userCode){
        return userRepository.existsByUsernameAndUserCodeNot(username, userCode);
    }

    public boolean existsByEmailAndUserCodeNot(String email, Integer userCode){
        return userRepository.existsByEmailAndUserCodeNot(email, userCode);
    }

    public boolean existsByPhoneAndUserCodeNot(String phone, String userCode){
        return userRepository.existsByPhoneAndUserCodeNot(phone, Integer.valueOf(userCode));
    }

    public User findUserByUserCode(String userCode) {
        return userRepository.findUserByUserCode(Integer.valueOf(userCode));
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
    public boolean updateUser2(UserRequestDto userDto, HttpServletRequest request) {
        Integer code = Integer.valueOf(userDto.getCode());

        User target = userRepository.findUserByUserCode(code);

        if(target == null)
            return false;

        target.update2(userDto);

        HttpSession session = request.getSession();
        session.setAttribute("authUser", target);

        return true;
    }

    @Transactional
    public boolean updateUser3(User user) {
        User target = userRepository.findUserByUserCode(user.getUserCode());

        if(target == null)
            return false;

        target.update3();
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
            return true;
        } else if(accountType.equals("user") && status.equals("pending")) {
            userRepository.delete(target);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteUserByUserCode(Integer userCode) {
        User target = userRepository.findUserByUserCode(userCode);

        if(target == null)
            return false;

        userRepository.delete(target);
        return true;
    }
}
