package com.drscan.web.primary.users.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findUserByUsername(String username);
    public User findUserByEmail(String email);
    public User findUserByPhone(String phone);

    public List<User> findAllByAccountType(String accountType);
    public List<User> findAllByAccountTypeAndStatus(String accountType, String status);
}
