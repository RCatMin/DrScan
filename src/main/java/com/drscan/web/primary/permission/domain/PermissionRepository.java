package com.drscan.web.primary.permission.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    public Permission findByUserCode(int userCode);

}
