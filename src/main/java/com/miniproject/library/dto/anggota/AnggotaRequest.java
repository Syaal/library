package com.miniproject.library.dto.anggota;

import lombok.Data;

@Data
public class AnggotaRequest {
    private Long nik;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String gender;
}
