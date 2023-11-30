package com.miniproject.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "Masukkan Nama")
    private String nama;
    @NotBlank(message = "Masukkan alamat")
    private String alamat;
    @NotBlank(message = "Masukkan email")
    private String email;
}