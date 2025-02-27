package com.drscan.web.primary.permission.service;

import com.drscan.web.primary.permission.domain.Permission;
import com.drscan.web.primary.permission.domain.PermissionRepository;
import com.drscan.web.primary.users.domain.User;
import com.drscan.web.primary.users.domain.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public boolean checkAdmin(int userCode){
        Permission permission = permissionRepository.findByUserCode(userCode);

        if(permission.getPermission().equals("admin")){
            return true;
        } else {
            return false;
        }
    }

    public void save(User user){
        Permission permission = new Permission(user.getUserCode(), user.getAccountType());

        permissionRepository.save(permission);
    }
}