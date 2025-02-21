package com.drscan.web.primary.permission.service;


import com.drscan.web.primary.permission.domain.Permission;
import com.drscan.web.primary.permission.domain.PermissionRepository;
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
        } else{
            return false;
        }
    }
}