package com.edig.imbackend.users.mapper;

import com.edig.imbackend.users.dto.UserDTO;
import com.edig.imbackend.users.dto.UserRequestDto;
import com.edig.imbackend.users.entities.User;
import com.edig.imbackend.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }

    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public UserRequestDto toUserRequestDto(User user) {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName(user.getName());
        userRequestDto.setEmail(user.getEmail());
        userRequestDto.setUsername(user.getUsername());
        return userRequestDto;
    }

}
