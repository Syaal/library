package com.miniproject.library.dto.login;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "Masukkan email")
    private String email;
    @NotBlank(message = "Masukkan password")

    private String password;

}
