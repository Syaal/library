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
    @NotBlank(message = "Masukkan Nama")
    private String nama;
    @NotBlank(message = "Masukkan alamat")
    private String alamat;
    @NotBlank(message = "Masukkan email")
    private String email;
    @NotBlank(message = "Masukkan Password")
    private String password;
}
