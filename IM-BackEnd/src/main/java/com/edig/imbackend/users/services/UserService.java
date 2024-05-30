package com.edig.imbackend.users.services;

import com.edig.imbackend.users.dto.UserDTO;
import com.edig.imbackend.users.dto.UserRequestDto;

import java.util.List;

public interface UserService {
    List<UserRequestDto> findAll();

    UserRequestDto findById(String id);

    UserRequestDto findByUsername(String username);

    UserRequestDto findByEmail(String email);

    UserRequestDto findByUsernameOrEmail(String username, String email);

    UserRequestDto save(UserDTO user);

    void deleteByUsername(String id);

    UserRequestDto update(UserRequestDto user, String id);

    void updatePassword(String username, String password, String newPassword);
}
