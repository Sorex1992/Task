package com.example.task.service.impl;

import com.example.task.dto.UserDTO;
import com.example.task.exception.ConflictException;
import com.example.task.exception.NotFoundException;
import com.example.task.model.Role;
import com.example.task.model.User;
import com.example.task.repo.RoleRepository;
import com.example.task.repo.UserRepository;
import com.example.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Transactional
    @Override
    public User createUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getName()).isPresent()) {
            throw new ConflictException(String.format("User with name: %s already exist", userDTO.getName()));
        }
        List<Role> userRoles = userDTO.getRoles().stream()
                .map(roleDTO -> roleRepository.findByName(roleDTO.getName()))
                .collect(Collectors.toList());
        var user = User.builder()
                .username(userDTO.getName())
                .password(userDTO.getPassword())
                .roles(userRoles)
                .build();
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        List<Role> updatedRoles = userDTO.getRoles().stream()
                .map(roleDTO -> roleRepository.findByName(roleDTO.getName()))
                .collect(Collectors.toList());
        User updatedUser = User.builder()
                .username(userDTO.getName())
                .password(userDTO.getPassword())
                .roles(updatedRoles)
                .build();

        return userRepository.save(user);
    }
}
