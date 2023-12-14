package com.miniproject.library.dto.register;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "Masukkan Username")
    private String username;
    @NotBlank(message = "Masukkan Password")
    private String password;
}
