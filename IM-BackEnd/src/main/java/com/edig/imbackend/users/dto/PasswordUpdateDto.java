package com.edig.imbackend.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordUpdateDto {
    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter.")
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one number.")
    @Pattern(regexp = ".*[@#$%^&+=!¡].*", message = "Password must contain at least one special character.")

    private String password;
    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter.")
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one number.")
    @Pattern(regexp = ".*[@#$%^&+=!¡].*", message = "Password must contain at least one special character.")
    private String newPassword;
}
