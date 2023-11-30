package com.miniproject.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private Integer id;
    private String nama;
    private String alamat;
    private String email;
    private String token;
}
