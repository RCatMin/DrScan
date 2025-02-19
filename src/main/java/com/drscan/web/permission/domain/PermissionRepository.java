package com.drscan.web.permission.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    public Permission findByUserCode(int userCode);

}
