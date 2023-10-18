package com.example.task.service.impl;

import com.example.task.dto.RoleDTO;
import com.example.task.model.Role;
import com.example.task.repo.RoleRepository;
import com.example.task.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Transactional
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    @Transactional
    @Override
    public Role createRole(RoleDTO roleDTO) {
        Role role = Role.builder()
                .name(roleDTO.getName())
                .build();
        return roleRepository.save(role);
    }
}
