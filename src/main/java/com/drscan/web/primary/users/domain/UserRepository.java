package com.drscan.web.primary.users.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findUserByUsername(String username);
    public User findUserByEmail(String email);
    public User findUserByPhone(String phone);
}
