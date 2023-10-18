package com.example.task.service;

import com.example.task.dto.UserDTO;
import com.example.task.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User createUser(UserDTO userDto);

    User getUserById(Long userId);

    User updateUser(Long id, UserDTO userDTO);
}
