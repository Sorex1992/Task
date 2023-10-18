package com.example.task.service;

import com.example.task.dto.RoleDTO;
import com.example.task.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role createRole(RoleDTO roleDTO);
}
