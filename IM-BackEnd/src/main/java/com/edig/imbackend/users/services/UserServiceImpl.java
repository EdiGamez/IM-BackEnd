package com.edig.imbackend.users.services;

import com.edig.imbackend.auth.entities.Rol;
import com.edig.imbackend.auth.repository.RolRepository;
import com.edig.imbackend.users.dto.UserDTO;
import com.edig.imbackend.users.dto.UserRequestDto;
import com.edig.imbackend.users.entities.User;
import com.edig.imbackend.users.mapper.UserMapper;
import com.edig.imbackend.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RolRepository rolRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserRequestDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserRequestDto).toList();
    }

    @Override
    public UserRequestDto findById(String id) {
        User user = userRepository.findById(id).orElse(null);
        return userMapper.toUserRequestDto(user);
    }

    @Override
    public UserRequestDto findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        return userMapper.toUserRequestDto(user.get());
    }

    @Override
    public UserRequestDto findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found with email: " + email);
        }
        return userMapper.toUserRequestDto(user.get());
    }

    @Override
    public UserRequestDto findByUsernameOrEmail(String username, String email) {
        Optional<User> user = userRepository.findByUsernameOrEmail(username, email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found with username: " + username + " or email: " + email);
        }
        return userMapper.toUserRequestDto(user.get());
    }

    @Override
    public UserRequestDto save(UserDTO user) {
        User userEntity = userMapper.toUser(user);
        if(userRepository.findByUsername(userEntity.getUsername()).isPresent()) {
            System.out.println(userRepository.findByUsername(userEntity.getUsername()));
            throw new RuntimeException("Error: Username is already taken!");
        }
        if(userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
            System.out.println(userRepository.findByEmail(userEntity.getEmail()));
            throw new RuntimeException("Error: Email is already in use!");
        }
        Rol userRole = rolRepository.findById("1").orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        userEntity.setRoles(Arrays.asList(userRole));
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
        return userMapper.toUserRequestDto(userEntity);
    }
    @Override
    public UserRequestDto update(UserRequestDto user, String username) {
        User userEntity = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        userEntity.setName(user.getName());
        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());

        userRepository.save(userEntity);
        return userMapper.toUserRequestDto(userEntity);
    }

    @Override
    public void updatePassword(String username, String password, String newPassword) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Error: Password is incorrect.");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
