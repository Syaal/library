package com.miniproject.library.dto.anggota;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnggotaResponse {
    private Integer id;
    private Long nik;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String gender;
}
