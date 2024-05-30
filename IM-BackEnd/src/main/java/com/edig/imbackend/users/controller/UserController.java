package com.edig.imbackend.users.controller;

import com.edig.imbackend.commons.ApiResponse;
import com.edig.imbackend.users.dto.PasswordUpdateDto;
import com.edig.imbackend.users.dto.UserDTO;
import com.edig.imbackend.users.dto.UserRequestDto;
import com.edig.imbackend.users.entities.User;
import com.edig.imbackend.users.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserRequestDto>>> findAll() {
        List<UserRequestDto> users = userService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Users fetched successfully", users));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "Access Denied", null));
        }
        Optional<UserRequestDto> user = Optional.ofNullable(userService.findByUsername(username));
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/user/username-or-email")
    public ResponseEntity<?> getUserByUsernameOrEmail(@RequestParam String username, @RequestParam String email) {
        Optional<UserRequestDto> user = Optional.ofNullable(userService.findByUsernameOrEmail(username, email));
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserRequestDto>> saveUser(@Valid @RequestBody UserDTO user) {
        UserRequestDto savedUser = userService.save(user);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "User created successfully", savedUser));
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @Valid @RequestBody UserRequestDto user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "Access Denied", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully", userService.update(user, username)));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam String username) {
        userService.deleteByUsername(username);
        return ResponseEntity.ok().body(new ApiResponse<>(HttpStatus.OK.value(), "User deleted successfully", null));
    }

    @PostMapping("/{username}/update-password")
    public ResponseEntity<?> updatePassword(@PathVariable String username, @Valid @RequestBody PasswordUpdateDto passwordUpdateDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "Access Denied", null));
        }
        userService.updatePassword(username, passwordUpdateDto.getPassword(), passwordUpdateDto.getNewPassword());
        return ResponseEntity.ok().body(    new ApiResponse<>(HttpStatus.OK.value(),"Password Updated Successful",null));
    }

}
