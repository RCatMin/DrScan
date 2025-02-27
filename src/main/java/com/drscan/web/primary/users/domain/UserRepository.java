package com.drscan.web.primary.users.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUserCode(Integer userCode);

    boolean existsByUsernameAndUserCodeNot(String username, Integer userCode);
    boolean existsByEmailAndUserCodeNot(String email, Integer userCode);
    boolean existsByPhoneAndUserCodeNot(String phone, Integer userCode);

    User findUserByUsername(String username);
    User findUserByEmail(String email);
    User findUserByPhone(String phone);

    List<User> findAllByAccountType(String accountType);
    List<User> findAllByAccountTypeAndStatus(String accountType, String status);
}
