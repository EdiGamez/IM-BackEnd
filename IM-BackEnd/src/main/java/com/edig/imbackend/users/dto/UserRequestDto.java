package com.edig.imbackend.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank
    private String name;
    @Email
    @NotBlank
    @Pattern(regexp = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[a-zA-Z]{2,})$", message = "Email can only contain letters, numbers, underscores, and periods.")
    private String email;
    @NotBlank
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^[\\w-]+$", message = "Username can only contain letters, numbers, and underscores.")
    private String username;
}
