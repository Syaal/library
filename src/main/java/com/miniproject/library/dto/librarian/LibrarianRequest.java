package com.miniproject.library.dto.librarian;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LibrarianRequest {
    @NotBlank(message = "Masukan Nama")
    private String name;
    @NotBlank(message = "Masukkan alamat")
    private String address;
    @NotBlank(message = "Masukkan email")
    private String email;
    @NotBlank(message = "Masukkan Jenis Kelamin")
    private String gender;
}
